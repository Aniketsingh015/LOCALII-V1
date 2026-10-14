package com.localii.backend.identity.service;

import com.localii.backend.common.exception.DuplicateResourceException;
import com.localii.backend.common.exception.ResourceNotFoundException;
import com.localii.backend.identity.dto.CreateVendorProfileRequest;
import com.localii.backend.identity.dto.VendorProfileResponse;
import com.localii.backend.identity.entity.VendorProfile;
import com.localii.backend.identity.entity.VerificationStatus;
import com.localii.backend.identity.repository.UserRepository;
import com.localii.backend.identity.repository.VendorProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VendorProfileService {

    private final VendorProfileRepository vendorProfileRepository;
    private final UserRepository userRepository;

    @Transactional
    public VendorProfileResponse create(UUID userId, CreateVendorProfileRequest request) {
        if (vendorProfileRepository.existsByUserId(userId)) {
            throw new DuplicateResourceException("Vendor profile already exists");
        }

        VendorProfile profile = new VendorProfile();
        profile.setUser(userRepository.getReferenceById(userId));
        profile.setShopName(request.getShopName());
        profile.setDescription(request.getDescription());
        profile.setAddress(request.getAddress());
        profile.setCity(request.getCity());
        profile.setBusinessHours(request.getBusinessHours());
        profile.setVerificationStatus(VerificationStatus.PENDING);

        return new VendorProfileResponse(vendorProfileRepository.save(profile));
    }

    @Transactional(readOnly = true)
    public VendorProfileResponse getMine(UUID userId) {
        VendorProfile profile = vendorProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor profile not found"));
        return new VendorProfileResponse(profile);
    }
}