package com.sintu.issue_tracker.repository;

import com.sintu.issue_tracker.model.Ticket;
import com.sintu.issue_tracker.model.TicketStatus;
import com.sintu.issue_tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByStatus(TicketStatus status);
    
    // Finds tickets where the "createdBy" column matches the User object
    List<Ticket> findByCreatedBy(User user);

    // Count tickets by status
    long countByStatus(TicketStatus status);
}