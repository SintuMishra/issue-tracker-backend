package com.sintu.issue_tracker.service;

import com.sintu.issue_tracker.dto.CreateTicketRequest;
import com.sintu.issue_tracker.dto.TicketResponse;
import com.sintu.issue_tracker.model.Priority;
import com.sintu.issue_tracker.model.Ticket;
import com.sintu.issue_tracker.model.TicketStatus;
import com.sintu.issue_tracker.model.User;
import com.sintu.issue_tracker.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public TicketResponse createTicket(CreateTicketRequest request, User user) {
        Ticket ticket = new Ticket();
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setCategory(request.getCategory());
        ticket.setBlockName(request.getBlockName()); 
        ticket.setRoomNo(request.getRoomNo());
        // Default to MEDIUM if no priority is sent
        ticket.setPriority(request.getPriority() != null ? request.getPriority() : Priority.MEDIUM);
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setCreatedBy(user);

        Ticket savedTicket = ticketRepository.save(ticket);
        return mapToResponse(savedTicket);
    }

    // For Students: Get only their tickets
    public List<TicketResponse> getTicketsByUser(User user) {
        return ticketRepository.findByCreatedBy(user)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // For Admin: Get ALL tickets
    public List<TicketResponse> getAllTickets() {
        return ticketRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // 👇 THIS WAS MISSING: For Admin to filter by status
    public List<TicketResponse> getTicketsByStatus(TicketStatus status) {
        return ticketRepository.findByStatus(status)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private TicketResponse mapToResponse(Ticket ticket) {
        return TicketResponse.builder()
                .id(ticket.getId())
                .title(ticket.getTitle())
                .description(ticket.getDescription())
                .category(ticket.getCategory())
                .priority(ticket.getPriority())
                .status(ticket.getStatus())
                .blockName(ticket.getBlockName())
                .roomNo(ticket.getRoomNo())
                .location(ticket.getLocation())
                .createdByName(ticket.getCreatedBy().getName())
                // Safe check for null assigned user
                .assignedToName(ticket.getAssignedTo() != null ? ticket.getAssignedTo().getName() : null)
                .createdAt(ticket.getCreatedAt())
                .resolvedAt(ticket.getResolvedAt())
                .build();
    }
}