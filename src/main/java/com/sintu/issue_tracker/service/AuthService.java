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

    private static final String ADMIN_SECRET_CODE = "MISHRA_BOSS_2025";

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
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Role + admin key check
        if (request.getRole() == Role.ADMIN) {
            if (request.getAdminKey() == null ||
                !ADMIN_SECRET_CODE.equals(request.getAdminKey())) {
                throw new IllegalArgumentException("Invalid admin security code");
            }
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.STUDENT);
        }

        user.setDepartment(request.getDepartment());
        user.setHostel(request.getHostel());

        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);

        return new AuthResponse(jwtToken, user.getName(), user.getRole().name(), user.getId());
    }

    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
                )
            );
        } catch (Exception e) {
            // unify invalid credentials message
            throw new IllegalArgumentException("Invalid email or password");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String jwtToken = jwtService.generateToken(user);

        return new AuthResponse(jwtToken, user.getName(), user.getRole().name(), user.getId());
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + email));
    }
}
