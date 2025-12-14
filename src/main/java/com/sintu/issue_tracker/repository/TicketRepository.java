package com.sintu.issue_tracker.repository;

import com.sintu.issue_tracker.model.Ticket;
import com.sintu.issue_tracker.model.TicketStatus;
import com.sintu.issue_tracker.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    // Existing methods
    List<Ticket> findByCreatedBy(User user);

    List<Ticket> findByAssignedTo(User user);

    // New methods for admin dashboard

    // Filter by status (this is enough for now)
    Page<Ticket> findByStatus(TicketStatus status, Pageable pageable);

    // Statistics for status
    long countByStatus(TicketStatus status);

    // If your Ticket has a field "category", you can keep this.
    // If not sure, comment it out for now.
    // long countByCategory(String category);
}
