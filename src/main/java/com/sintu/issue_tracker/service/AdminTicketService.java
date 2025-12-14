package com.sintu.issue_tracker.service;

import com.sintu.issue_tracker.dto.AssignTicketRequest;
import com.sintu.issue_tracker.dto.TicketResponseDto;
import com.sintu.issue_tracker.dto.TicketStatsDto;
import com.sintu.issue_tracker.model.Ticket;
import com.sintu.issue_tracker.model.TicketStatus;
import com.sintu.issue_tracker.model.User;
import com.sintu.issue_tracker.repository.TicketRepository;
import com.sintu.issue_tracker.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;  // <-- add this

@Service
public class AdminTicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public AdminTicketService(TicketRepository ticketRepository,
                              UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    // OLD method (still can be used if you want paging)
    public Page<TicketResponseDto> getAllTickets(String status,
                                                 Pageable pageable) {
        if (status != null && !status.isEmpty()) {
            TicketStatus st = TicketStatus.valueOf(status);
            return ticketRepository.findByStatus(st, pageable)
                    .map(this::toDto);
        }
        return ticketRepository.findAll(pageable)
                .map(this::toDto);
    }

    // NEW: simple list for React admin table
    public List<TicketResponseDto> getAllTicketsSimple(String status) {
        if (status != null && !status.isEmpty()) {
            TicketStatus st = TicketStatus.valueOf(status);
            return ticketRepository
                    .findByStatus(st, Pageable.unpaged())
                    .map(this::toDto)
                    .toList();
        }

        return ticketRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    // 2) Assign ticket to staff
    public TicketResponseDto assignTicket(Long ticketId,
                                          AssignTicketRequest request) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        User staff = userRepository.findById(request.getStaffUserId())
                .orElseThrow(() -> new RuntimeException("Staff user not found"));

        ticket.setAssignedTo(staff);

        if (request.getStatus() != null) {
            ticket.setStatus(request.getStatus());
        }

        Ticket saved = ticketRepository.save(ticket);
        return toDto(saved);
    }

    // 3) Basic stats for dashboard cards
    public TicketStatsDto getStats() {
        TicketStatsDto dto = new TicketStatsDto();
        dto.setTotalTickets(ticketRepository.count());
        dto.setOpenTickets(ticketRepository.countByStatus(TicketStatus.OPEN));
        dto.setInProgressTickets(ticketRepository.countByStatus(TicketStatus.IN_PROGRESS));
        dto.setResolvedTickets(ticketRepository.countByStatus(TicketStatus.RESOLVED));
        dto.setClosedTickets(ticketRepository.countByStatus(TicketStatus.CLOSED));
        return dto;
    }

    public TicketResponseDto updateStatus(Long ticketId, TicketStatus newStatus) {
    Ticket ticket = ticketRepository.findById(ticketId)
            .orElseThrow(() -> new RuntimeException("Ticket not found"));

    ticket.setStatus(newStatus);
    Ticket saved = ticketRepository.save(ticket);
    return toDto(saved);
    }

    // Helper: convert Ticket entity -> TicketResponseDto
    private TicketResponseDto toDto(Ticket ticket) {
        TicketResponseDto dto = new TicketResponseDto();
        dto.setId(ticket.getId());
        dto.setTitle(ticket.getTitle());
        dto.setDescription(ticket.getDescription());
        dto.setCategory(ticket.getCategory());
        dto.setLocation(ticket.getLocation());
        dto.setPriority(ticket.getPriority());
        dto.setStatus(ticket.getStatus());
        dto.setCreatedById(ticket.getCreatedBy().getId());
        dto.setAssignedToId(
                ticket.getAssignedTo() != null ? ticket.getAssignedTo().getId() : null
        );
        dto.setCreatedAt(ticket.getCreatedAt().toString());
        dto.setUpdatedAt(ticket.getUpdatedAt().toString());
        return dto;
    }
}
