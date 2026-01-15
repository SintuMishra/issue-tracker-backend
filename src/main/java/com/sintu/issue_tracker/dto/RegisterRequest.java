// src/main/java/com/sintu/issue_tracker/dto/RegisterRequest.java
package com.sintu.issue_tracker.dto;

import com.sintu.issue_tracker.model.Role;

public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Role role;
    private String adminKey; 
    private String department;
    private String hostel;
    
    // Security and Staff fields
    private String staffId;       // Mapped in AuthService if role is STAFF
    private String specialization; // e.g., PLUMBER, ELECTRICIAN

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

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getHostel() { return hostel; }
    public void setHostel(String hostel) { this.hostel = hostel; }

    public String getStaffId() { return staffId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
}