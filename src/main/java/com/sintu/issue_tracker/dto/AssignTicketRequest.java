package com.sintu.issue_tracker.dto;

import com.sintu.issue_tracker.model.TicketStatus;

public class AssignTicketRequest {

    private Long staffUserId;     // which staff to assign
    private TicketStatus status;  // e.g. ASSIGNED or IN_PROGRESS

    public Long getStaffUserId() {
        return staffUserId;
    }

    public void setStaffUserId(Long staffUserId) {
        this.staffUserId = staffUserId;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }
}
