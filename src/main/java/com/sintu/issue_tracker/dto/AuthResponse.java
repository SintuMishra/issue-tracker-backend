package com.sintu.issue_tracker.dto;

import com.sintu.issue_tracker.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponse {
    private Long id;
    private String name;
    private String email;
    private Role role;
}
