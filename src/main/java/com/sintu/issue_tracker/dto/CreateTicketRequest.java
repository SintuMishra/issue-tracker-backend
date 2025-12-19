package com.sintu.issue_tracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty; // Import this!
import com.sintu.issue_tracker.model.Priority;
import lombok.Data;

@Data
public class CreateTicketRequest {
    private String title;
    private String description;
    private String category;
    
    // 👇 The fix: Map JSON "block" to Java "blockName"
    @JsonProperty("block") 
    private String blockName; 

    private String roomNo;
    
    private Priority priority; 
}