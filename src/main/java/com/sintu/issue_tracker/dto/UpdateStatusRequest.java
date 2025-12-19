package com.sintu.issue_tracker.dto;

import lombok.Data;

@Data
public class UpdateStatusRequest {
    // 👇 Change this to String so the Controller can handle case-conversion
    private String status;
}