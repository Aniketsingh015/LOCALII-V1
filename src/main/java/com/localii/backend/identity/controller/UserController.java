package com.localii.backend.identity.controller;   //1)which folder the files live in 

import com.localii.backend.identity.dto.LoginRequest;
// 2)classes which this file wants to use
import com.localii.backend.identity.dto.RegisterRequest;
import com.localii.backend.identity.dto.UserResponse;
import com.localii.backend.identity.service.UserService;
import jakarta.validation.Valid;//jakarta is from jpa
import lombok.RequiredArgsConstructor;//lombok is from lombok
import org.springframework.http.HttpStatus; //this is from spring
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 3)labels on the class
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController { //the class

    private final UserService userService; //5)fields what the class holds

    @PostMapping("/register") //6)labels on the method
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) { //what it can do
        UserResponse response = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }
}