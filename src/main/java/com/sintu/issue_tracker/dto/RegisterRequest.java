package com.sintu.issue_tracker.dto;

import com.sintu.issue_tracker.model.Role;

public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Role role;      // This is an Enum, not a String
    private String adminKey; 
    
    // 👇 NEW FIELDS ADDED
    private String department;
    private String hostel;

    // --- Getters and Setters ---
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public String getAdminKey() { return adminKey; }
    public void setAdminKey(String adminKey) { this.adminKey = adminKey; }

    // 👇 NEW GETTERS/SETTERS
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getHostel() { return hostel; }
    public void setHostel(String hostel) { this.hostel = hostel; }
}