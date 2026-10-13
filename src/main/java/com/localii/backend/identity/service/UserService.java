package com.localii.backend.identity.service;

import com.localii.backend.identity.dto.LoginRequest;
import com.localii.backend.identity.dto.LoginResponse;

import com.localii.backend.identity.dto.RegisterRequest;
import com.localii.backend.identity.dto.UserResponse;
import com.localii.backend.identity.entity.User;
import com.localii.backend.identity.repository.UserRepository;
import com.localii.backend.identity.security.JwtService;
import com.localii.backend.common.exception.DuplicateResourceException;
import com.localii.backend.common.exception.InvalidCredentialsException;
import com.localii.backend.common.exception.ForbiddenOperationException;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserResponse register(RegisterRequest request) {
        if (request.getRole() == User.Role.ADMIN) {
          throw new ForbiddenOperationException("Admin accounts cannot be self-registered");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already registered");
         }
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new DuplicateResourceException("Phone already registered");
         }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        User.Status status = (request.getRole() == User.Role.CUSTOMER)
        ? User.Status.ACTIVE
        : User.Status.PENDING;
        user.setStatus(status);

        User saved = userRepository.save(user);
        return new UserResponse(saved);
    }

    public LoginResponse login(LoginRequest request) {
    User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(InvalidCredentialsException::new);

    if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
        throw new InvalidCredentialsException();
    }

    if (user.getStatus() == User.Status.SUSPENDED) {
         throw new ForbiddenOperationException("Account is suspended");
    }   

    String token = jwtService.generateToken(user);
    return new LoginResponse(token, new UserResponse(user));
}
}