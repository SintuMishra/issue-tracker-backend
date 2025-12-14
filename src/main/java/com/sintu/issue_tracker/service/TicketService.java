package com.sintu.issue_tracker.service;

import com.sintu.issue_tracker.dto.CreateTicketRequest;
import com.sintu.issue_tracker.dto.TicketResponse;
import com.sintu.issue_tracker.dto.UpdateTicketStatusRequest;
import com.sintu.issue_tracker.model.Ticket;
import com.sintu.issue_tracker.model.TicketStatus;
import com.sintu.issue_tracker.model.User;
import com.sintu.issue_tracker.repository.TicketRepository;
import com.sintu.issue_tracker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public TicketService(TicketRepository ticketRepository,
                         UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    // CREATE TICKET
    public TicketResponse createTicket(CreateTicketRequest request) {
        User creator = userRepository.findById(request.getCreatedByUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Ticket ticket = Ticket.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .location(request.getLocation())
                .priority(request.getPriority())
                .status(TicketStatus.OPEN)
                .createdBy(creator)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Ticket saved = ticketRepository.save(ticket);

        return new TicketResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getCategory(),
                saved.getLocation(),
                saved.getPriority(),
                saved.getStatus()
        );
    }

    // LIST TICKETS FOR A USER
    public List<TicketResponse> getTicketsByUser(Long userId) {
        User creator = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ticketRepository.findByCreatedBy(creator)
                .stream()
                .map(t -> new TicketResponse(
                        t.getId(),
                        t.getTitle(),
                        t.getDescription(),
                        t.getCategory(),
                        t.getLocation(),
                        t.getPriority(),
                        t.getStatus()
                ))
                .toList();
    }

    // UPDATE STATUS / ASSIGNMENT
    public TicketResponse updateTicketStatus(Long ticketId, UpdateTicketStatusRequest request) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        if (request.getAssignedToUserId() != null) {
            User assignee = userRepository.findById(request.getAssignedToUserId())
                    .orElseThrow(() -> new RuntimeException("Assignee not found"));
            ticket.setAssignedTo(assignee);
        }

        if (request.getStatus() != null) {
            ticket.setStatus(request.getStatus());
        }

        ticket.setUpdatedAt(LocalDateTime.now());
        if (ticket.getStatus() == TicketStatus.RESOLVED
                || ticket.getStatus() == TicketStatus.CLOSED) {
            ticket.setResolvedAt(LocalDateTime.now());
        }

        Ticket saved = ticketRepository.save(ticket);

        return new TicketResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getCategory(),
                saved.getLocation(),
                saved.getPriority(),
                saved.getStatus()
        );
    }
}