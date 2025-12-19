package com.sintu.issue_tracker.service;

import com.sintu.issue_tracker.dto.AuthResponse;
import com.sintu.issue_tracker.dto.LoginRequest;
import com.sintu.issue_tracker.dto.RegisterRequest;
import com.sintu.issue_tracker.model.Role;
import com.sintu.issue_tracker.model.User;
import com.sintu.issue_tracker.repository.UserRepository;
import com.sintu.issue_tracker.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, 
                       PasswordEncoder passwordEncoder, 
                       JwtService jwtService, 
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        
        // 🔒 SECURITY CHECK (Fixed Type Mismatch)
        // Since request.getRole() is an Enum, we compare it directly
        if (request.getRole() == Role.ADMIN) {
            String SECRET_CODE = "MISHRA_BOSS_2025"; 

            // Check if key is provided and correct
            if (request.getAdminKey() == null || !request.getAdminKey().equals(SECRET_CODE)) {
                throw new RuntimeException("Security Alert: Invalid Admin Secret Key!");
            }
            user.setRole(Role.ADMIN);
        } else {
            // Default to Student
            user.setRole(Role.STUDENT);
        }

        // These now work because we updated RegisterRequest.java
        user.setDepartment(request.getDepartment());
        user.setHostel(request.getHostel());

        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);

        return new AuthResponse(jwtToken, user.getName(), user.getRole().name(), user.getId());
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String jwtToken = jwtService.generateToken(user);

        return new AuthResponse(jwtToken, user.getName(), user.getRole().name(), user.getId());
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));
    }
}