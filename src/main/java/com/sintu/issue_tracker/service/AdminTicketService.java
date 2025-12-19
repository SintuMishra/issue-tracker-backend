package com.sintu.issue_tracker.service;

import com.sintu.issue_tracker.dto.AssignTicketRequest;
import com.sintu.issue_tracker.dto.TicketResponse;
import com.sintu.issue_tracker.model.Ticket;
import com.sintu.issue_tracker.model.TicketStatus;
import com.sintu.issue_tracker.model.User;
import com.sintu.issue_tracker.repository.TicketRepository;
import com.sintu.issue_tracker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminTicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public AdminTicketService(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    public List<TicketResponse> getAllTicketsSimple(String status) {
        List<Ticket> tickets;

        if (status != null && !status.isEmpty()) {
            try {
                TicketStatus ts = TicketStatus.valueOf(status.toUpperCase());
                tickets = ticketRepository.findAll().stream()
                        .filter(t -> t.getStatus() == ts)
                        .collect(Collectors.toList());
            } catch (IllegalArgumentException e) {
                tickets = ticketRepository.findAll();
            }
        } else {
            tickets = ticketRepository.findAll();
        }

        return tickets.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // 👇 FIXED: Assign logic now saves correctly and updates status
    public TicketResponse assignTicket(Long ticketId, AssignTicketRequest request) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        User staff = userRepository.findById(request.getStaffUserId())
                .orElseThrow(() -> new RuntimeException("Staff user not found"));

        // 1. Link the staff member
        ticket.setAssignedTo(staff);
        
        // 2. FORCE status to ASSIGNED (User wants to see this change!)
        ticket.setStatus(TicketStatus.ASSIGNED);

        // 3. Save to Database
        Ticket saved = ticketRepository.save(ticket);
        return mapToResponse(saved);
    }

    public TicketResponse updateStatus(Long ticketId, TicketStatus newStatus) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticket.setStatus(newStatus);
        Ticket saved = ticketRepository.save(ticket);
        return mapToResponse(saved);
    }

    // 👇 FIXED: Real stats instead of "0L"
    public Map<String, Long> getStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("totalTickets", ticketRepository.count());
        
        // Count actual tickets by status
        stats.put("openTickets", countByStatus(TicketStatus.OPEN));
        stats.put("assignedTickets", countByStatus(TicketStatus.ASSIGNED)); 
        stats.put("inProgressTickets", countByStatus(TicketStatus.IN_PROGRESS));
        stats.put("resolvedTickets", countByStatus(TicketStatus.RESOLVED));
        stats.put("closedTickets", countByStatus(TicketStatus.CLOSED));
        
        return stats;
    }
    
    // Helper for stats
    private Long countByStatus(TicketStatus status) {
        return ticketRepository.findAll().stream()
                .filter(t -> t.getStatus() == status)
                .count();
    }

    private TicketResponse mapToResponse(Ticket t) {
        return TicketResponse.builder()
                .id(t.getId())
                .title(t.getTitle())
                .description(t.getDescription())
                .category(t.getCategory())
                .blockName(t.getBlockName()) 
                .roomNo(t.getRoomNo())
                .location(t.getLocation())
                .priority(t.getPriority())
                .status(t.getStatus())
                .createdAt(t.getCreatedAt())
                .createdByName(t.getCreatedBy() != null ? t.getCreatedBy().getName() : "Unknown")
                .assignedToName(t.getAssignedTo() != null ? t.getAssignedTo().getName() : null)
                .build();
    }
}