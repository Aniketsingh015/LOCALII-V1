package com.localii.backend.identity.controller;

import com.localii.backend.identity.dto.CreateVendorProfileRequest;
import com.localii.backend.identity.dto.VendorProfileResponse;
import com.localii.backend.identity.service.VendorProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/vendors/profile")
@RequiredArgsConstructor
public class VendorProfileController {

    private final VendorProfileService vendorProfileService;

    @PostMapping
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<VendorProfileResponse> create(
            Authentication authentication,
            @Valid @RequestBody CreateVendorProfileRequest request) {
        UUID userId = UUID.fromString(authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(vendorProfileService.create(userId, request));
    }

    @GetMapping
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<VendorProfileResponse> getMine(Authentication authentication) {
        UUID userId = UUID.fromString(authentication.getName());
        return ResponseEntity.ok(vendorProfileService.getMine(userId));
    }
}