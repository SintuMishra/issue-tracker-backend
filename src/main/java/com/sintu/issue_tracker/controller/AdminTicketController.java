package com.sintu.issue_tracker.controller;

import com.sintu.issue_tracker.dto.AssignTicketRequest;
import com.sintu.issue_tracker.dto.TicketResponseDto;
import com.sintu.issue_tracker.dto.TicketStatsDto;
import com.sintu.issue_tracker.dto.UpdateStatusRequest;
import com.sintu.issue_tracker.service.AdminTicketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/tickets")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminTicketController {

    private final AdminTicketService adminTicketService;

    public AdminTicketController(AdminTicketService adminTicketService) {
        this.adminTicketService = adminTicketService;
    }

    // GET /api/admin/tickets?status=OPEN
    @GetMapping
    public List<TicketResponseDto> getAllTickets(
            @RequestParam(required = false) String status
    ) {
        return adminTicketService.getAllTicketsSimple(status);
    }

    // PUT /api/admin/tickets/{id}/assign
    @PutMapping("/{id}/assign")
    public TicketResponseDto assignTicket(
            @PathVariable Long id,
            @RequestBody AssignTicketRequest request
    ) {
        return adminTicketService.assignTicket(id, request);
    }

    // PUT /api/admin/tickets/{id}/status
    @PutMapping("/{id}/status")
    public TicketResponseDto updateStatus(
        @PathVariable Long id,
        @RequestBody UpdateStatusRequest request
    ) {
        return adminTicketService.updateStatus(id, request.getStatus());
    }

    // GET /api/admin/tickets/stats
    // GET /api/admin/tickets/stats
@GetMapping("/stats")
public TicketStatsDto getStats() {
    return adminTicketService.getStats();
}
}
