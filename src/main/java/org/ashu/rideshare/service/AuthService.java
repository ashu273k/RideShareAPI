package org.ashu.rideshare.service;

import org.ashu.rideshare.dto.LoginRequest;
import org.ashu.rideshare.dto.LoginResponse;
import org.ashu.rideshare.dto.RegisterRequest;
import org.ashu.rideshare.model.User;
import org.ashu.rideshare.repository.UserRepository;
import org.ashu.rideshare.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Register a new user
     * - Check if username already exists
     * - Encode password with BCrypt
     * - Save user to MongoDB
     */
    public void register(RegisterRequest request) {
        // Check if username already exists
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // Create new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        // Save to MongoDB
        userRepository.save(user);
    }

    /**
     * Login user
     * - Find user by username
     * - Verify password (compare encoded versions)
     * - Generate JWT token
     * - Return token + user info
     */
    public LoginResponse login(LoginRequest request) {
        // Find user by username
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        // Verify password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

        // Return response
        return new LoginResponse(token, user.getUsername(), user.getRole());
    }
}