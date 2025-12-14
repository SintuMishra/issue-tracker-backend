package com.sintu.issue_tracker.dto;

import com.sintu.issue_tracker.model.TicketPriority;
import com.sintu.issue_tracker.model.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TicketResponse {
    private Long id;
    private String title;
    private String description;
    private String category;
    private String location;
    private TicketPriority priority;
    private TicketStatus status;
}
