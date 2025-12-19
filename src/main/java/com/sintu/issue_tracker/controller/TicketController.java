package com.sintu.issue_tracker.controller;

import com.sintu.issue_tracker.dto.CreateTicketRequest;
import com.sintu.issue_tracker.dto.TicketResponse;
import com.sintu.issue_tracker.model.TicketStatus; // Ensure this import exists
import com.sintu.issue_tracker.model.User;
import com.sintu.issue_tracker.service.AuthService;
import com.sintu.issue_tracker.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(origins = "*") // Allows your React frontend to connect
public class TicketController {

    private final TicketService ticketService;
    private final AuthService authService;

    public TicketController(TicketService ticketService, AuthService authService) {
        this.ticketService = ticketService;
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<TicketResponse> createTicket(
            @RequestBody CreateTicketRequest request,
            Authentication authentication
    ) {
        String email = authentication.getName();
        User user = authService.getUserByEmail(email);
        return ResponseEntity.ok(ticketService.createTicket(request, user));
    }

    // 👇 UPDATED METHOD: Handles 'Status' param & Admin/Student logic
    @GetMapping
    public ResponseEntity<List<TicketResponse>> getTickets(
            Authentication authentication,
            @RequestParam(required = false) TicketStatus status // made Optional
    ) {
        String email = authentication.getName();
        User user = authService.getUserByEmail(email);

        // 1. If User is ADMIN -> Show ALL tickets (or filtered by status)
        if (user.getRole().name().equals("ADMIN")) {
            if (status != null) {
                return ResponseEntity.ok(ticketService.getTicketsByStatus(status));
            }
            return ResponseEntity.ok(ticketService.getAllTickets());
        }

        // 2. If User is STUDENT -> Show only THEIR tickets
        return ResponseEntity.ok(ticketService.getTicketsByUser(user));
    }
}