package com.sintu.issue_tracker.dto;

import com.sintu.issue_tracker.model.TicketPriority;
import com.sintu.issue_tracker.model.TicketStatus;

public class TicketResponseDto {

    private Long id;
    private String title;
    private String description;
    private String category;
    private String location;
    private TicketPriority priority;     // enum
    private TicketStatus status;
    private Long createdById;
    private Long assignedToId;
    private String hostel;
    private String department;
    private String createdAt;
    private String updatedAt;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    // IMPORTANT: use TicketPriority here
    public TicketPriority getPriority() {
        return priority;
    }
    public void setPriority(TicketPriority priority) {
        this.priority = priority;
    }

    public TicketStatus getStatus() {
        return status;
    }
    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public Long getCreatedById() {
        return createdById;
    }
    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    public Long getAssignedToId() {
        return assignedToId;
    }
    public void setAssignedToId(Long assignedToId) {
        this.assignedToId = assignedToId;
    }

    public String getHostel() {
        return hostel;
    }
    public void setHostel(String hostel) {
        this.hostel = hostel;
    }

    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
