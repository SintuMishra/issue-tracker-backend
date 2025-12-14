package com.sintu.issue_tracker.dto;

import com.sintu.issue_tracker.model.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Role role;        // STUDENT / STAFF / ADMIN
    private String department;
    private String hostel;
}
