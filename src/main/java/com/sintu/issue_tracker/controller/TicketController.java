package com.sintu.issue_tracker.controller;

import com.sintu.issue_tracker.dto.CreateTicketRequest;
import com.sintu.issue_tracker.dto.TicketResponse;
import com.sintu.issue_tracker.dto.UpdateTicketStatusRequest;
import com.sintu.issue_tracker.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(origins = "http://localhost:5173")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<TicketResponse> createTicket(@RequestBody CreateTicketRequest request) {
        return ResponseEntity.ok(ticketService.createTicket(request));
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<TicketResponse>> getTicketsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(ticketService.getTicketsByUser(userId));
    }

    @PutMapping("/{ticketId}/status")
    public ResponseEntity<TicketResponse> updateStatus(
            @PathVariable Long ticketId,
            @RequestBody UpdateTicketStatusRequest request
    ) {
        return ResponseEntity.ok(ticketService.updateTicketStatus(ticketId, request));
    }
}