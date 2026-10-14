package com.localii.backend.identity.controller;

import com.localii.backend.identity.dto.CreateDeliveryPartnerProfileRequest;
import com.localii.backend.identity.dto.DeliveryPartnerProfileResponse;
import com.localii.backend.identity.service.DeliveryPartnerProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/delivery-partners/profile")
@RequiredArgsConstructor
public class DeliveryPartnerProfileController {

    private final DeliveryPartnerProfileService service;

    @PostMapping
    @PreAuthorize("hasRole('DELIVERY_PARTNER')")
    public ResponseEntity<DeliveryPartnerProfileResponse> create(
            Authentication authentication,
            @Valid @RequestBody CreateDeliveryPartnerProfileRequest request) {
        UUID userId = UUID.fromString(authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new DeliveryPartnerProfileResponse(service.create(userId, request)));
    }

    @GetMapping
    @PreAuthorize("hasRole('DELIVERY_PARTNER')")
    public DeliveryPartnerProfileResponse getMine(Authentication authentication) {
        UUID userId = UUID.fromString(authentication.getName());
        return new DeliveryPartnerProfileResponse(service.getMine(userId));
    }
}