package com.sintu.issue_tracker.dto;

import com.sintu.issue_tracker.model.Priority;
import com.sintu.issue_tracker.model.TicketStatus;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class TicketResponse {
    private Long id;
    private String title;
    private String description;
    private String category;
    private Priority priority;
    private TicketStatus status;
    
    // Location details
    private String blockName;
    private String roomNo;
    private String location; // The combined string

    private String createdByName;
    private String assignedToName;

    private LocalDateTime createdAt;
    private LocalDateTime resolvedAt;
}