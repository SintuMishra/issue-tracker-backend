package com.sintu.issue_tracker.dto;

import com.sintu.issue_tracker.model.TicketStatus;

public class UpdateStatusRequest {

    private TicketStatus status;

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }
}
