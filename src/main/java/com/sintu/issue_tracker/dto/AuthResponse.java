package com.sintu.issue_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token; // The frontend looks for "user.token"
    private String name;
    private String role;
    private Long id;
}