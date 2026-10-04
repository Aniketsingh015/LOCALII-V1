package com.localii.backend.identity.service;

import com.localii.backend.identity.dto.RegisterRequest;
import com.localii.backend.identity.dto.UserResponse;
import com.localii.backend.identity.entity.User;
import com.localii.backend.identity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new IllegalArgumentException("Phone already registered");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        User saved = userRepository.save(user);
        return new UserResponse(saved);
    }
}