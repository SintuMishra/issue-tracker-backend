// Ticket.java
package com.sintu.issue_tracker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false)
    private String category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status;

    // The new fields
    private String blockName;
    private String roomNo;

    // 🔴 The problematic field (DB requires it, so we will auto-fill it)
    private String location; 

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime resolvedAt;

    // RELATIONSHIPS

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by_id", nullable = false)
    @JsonIgnoreProperties("password") // Don't send password in JSON
    private User createdBy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assigned_to_id")
    @JsonIgnoreProperties("password")
    private User assignedTo;

    // --- MAGIC FIX IS HERE 👇 ---
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.status = (this.status == null) ? TicketStatus.OPEN : this.status;
        
        // Fix: If location is missing, build it from Block + Room
        if (this.location == null) {
            this.location = (this.blockName != null ? this.blockName : "") 
                          + " - " 
                          + (this.roomNo != null ? this.roomNo : "");
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}