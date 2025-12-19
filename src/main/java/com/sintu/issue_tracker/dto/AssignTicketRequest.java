package com.sintu.issue_tracker.dto;

import lombok.Data;

@Data
public class AssignTicketRequest {
    // We only need the ID. The backend will handle the status update automatically.
    private Long staffUserId; 
}