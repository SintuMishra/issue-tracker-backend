// AdminTicketService.java
package com.sintu.issue_tracker.service;

import com.sintu.issue_tracker.dto.AssignTicketRequest;
import com.sintu.issue_tracker.dto.TicketResponse;
import com.sintu.issue_tracker.model.Ticket;
import com.sintu.issue_tracker.model.TicketStatus;
import com.sintu.issue_tracker.model.User;
import com.sintu.issue_tracker.repository.TicketRepository;
import com.sintu.issue_tracker.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminTicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public AdminTicketService(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    // Paged list for admin (with optional status filter)
    public Page<TicketResponse> getAllTicketsSimple(String status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size); // 0-based page index
        Page<Ticket> ticketPage;

        if (status != null && !status.isEmpty()) {
            try {
                TicketStatus ts = TicketStatus.valueOf(status.toUpperCase());
                ticketPage = ticketRepository.findByStatus(ts, pageable);
            } catch (IllegalArgumentException e) {
                ticketPage = ticketRepository.findAll(pageable);
            }
        } else {
            ticketPage = ticketRepository.findAll(pageable);
        }

        return ticketPage.map(this::mapToResponse);
    }

    // Assign logic
    public TicketResponse assignTicket(Long ticketId, AssignTicketRequest request) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        User staff = userRepository.findById(request.getStaffUserId())
                .orElseThrow(() -> new RuntimeException("Staff user not found"));

        ticket.setAssignedTo(staff);
        ticket.setStatus(TicketStatus.ASSIGNED);

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

    // Stats
    public Map<String, Long> getStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("totalTickets", ticketRepository.count());
        stats.put("openTickets", ticketRepository.countByStatus(TicketStatus.OPEN));
        stats.put("assignedTickets", ticketRepository.countByStatus(TicketStatus.ASSIGNED));
        stats.put("inProgressTickets", ticketRepository.countByStatus(TicketStatus.IN_PROGRESS));
        stats.put("resolvedTickets", ticketRepository.countByStatus(TicketStatus.RESOLVED));
        stats.put("closedTickets", ticketRepository.countByStatus(TicketStatus.CLOSED));
        return stats;
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