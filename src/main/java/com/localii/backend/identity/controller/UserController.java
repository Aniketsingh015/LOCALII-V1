package com.localii.backend.identity.controller;   //1)which folder the files live in 
import java.util.Map;
import com.localii.backend.identity.dto.LoginRequest;
import com.localii.backend.identity.dto.LoginResponse;
// 2)classes which this file wants to use
import com.localii.backend.identity.dto.RegisterRequest;
import com.localii.backend.identity.dto.UserResponse;
import com.localii.backend.identity.service.UserService;
import jakarta.validation.Valid;//jakarta is from jpa
import lombok.RequiredArgsConstructor;//lombok is from lombok
import org.springframework.http.HttpStatus; //this is from spring
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;


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
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, String>> me(Authentication authentication) {
        return ResponseEntity.ok(Map.of(
                "userId", authentication.getName(),
                "role", authentication.getAuthorities().iterator().next().getAuthority()));
    }
}