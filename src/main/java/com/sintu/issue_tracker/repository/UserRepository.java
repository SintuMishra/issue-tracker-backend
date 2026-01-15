package com.sintu.issue_tracker.repository;

import com.sintu.issue_tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByStaffId(String staffId);

    // New: get all users with a specific role (e.g. "STAFF")
    List<User> findByRole(String role);
}
