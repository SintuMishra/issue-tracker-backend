package com.sintu.issue_tracker.dto;

import com.sintu.issue_tracker.model.TicketPriority;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTicketRequest {
    private String title;
    private String description;
    private String category;
    private String location;
    private TicketPriority priority;
    private Long createdByUserId;   // for now, pass user id from frontend
}
