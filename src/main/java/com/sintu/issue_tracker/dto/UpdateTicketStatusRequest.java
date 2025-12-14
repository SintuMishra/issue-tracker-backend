package com.sintu.issue_tracker.dto;

import com.sintu.issue_tracker.model.TicketStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTicketStatusRequest {
    private TicketStatus status;
    private Long assignedToUserId;   // can be null if only status is changing
}
