// src/main/java/com/sintu/issue_tracker/controller/AdminTicketController.java
package com.sintu.issue_tracker.controller;

import com.sintu.issue_tracker.dto.AssignTicketRequest;
import com.sintu.issue_tracker.dto.TicketResponse;
import com.sintu.issue_tracker.dto.UpdateTicketStatusRequest;
import com.sintu.issue_tracker.service.AdminTicketService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/tickets")
@CrossOrigin(origins = "*")
public class AdminTicketController {

    private final AdminTicketService adminTicketService;

    public AdminTicketController(AdminTicketService adminTicketService) {
        this.adminTicketService = adminTicketService;
    }

    // 1. Get All Tickets (With optional status filter + pagination)
    @GetMapping
    public ResponseEntity<Page<TicketResponse>> getAllTickets(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(adminTicketService.getAllTicketsSimple(status, page, size));
    }

    // 2. Assign a Ticket to Staff
    @PutMapping("/{id}/assign")
    public ResponseEntity<TicketResponse> assignTicket(
            @PathVariable Long id,
            @RequestBody AssignTicketRequest request
    ) {
        return ResponseEntity.ok(adminTicketService.assignTicket(id, request));
    }

    // 3. Update Ticket Status (JSON body)
    @PutMapping("/{id}/status")
    public ResponseEntity<TicketResponse> updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateTicketStatusRequest request
    ) {
        return ResponseEntity.ok(
                adminTicketService.updateStatus(id, request.getStatus())
        );
    }

    // 4. Get Dashboard Stats
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getStats() {
        return ResponseEntity.ok(adminTicketService.getStats());
    }
}
