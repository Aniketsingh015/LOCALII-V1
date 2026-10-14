package com.localii.backend.identity.service;

import com.localii.backend.common.exception.DuplicateResourceException;
import com.localii.backend.common.exception.ResourceNotFoundException;
import com.localii.backend.identity.dto.CreateDeliveryPartnerProfileRequest;
import com.localii.backend.identity.entity.DeliveryPartnerProfile;
import com.localii.backend.identity.entity.VerificationStatus;
import com.localii.backend.identity.repository.DeliveryPartnerProfileRepository;
import com.localii.backend.identity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryPartnerProfileService {

    private final DeliveryPartnerProfileRepository profileRepository;
    private final UserRepository userRepository;

    @Transactional
    public DeliveryPartnerProfile create(UUID userId, CreateDeliveryPartnerProfileRequest request) {
        if (profileRepository.existsByUserId(userId)) {
            throw new DuplicateResourceException("Delivery partner profile already exists");
        }
        DeliveryPartnerProfile profile = new DeliveryPartnerProfile();
        profile.setUser(userRepository.getReferenceById(userId));
        profile.setVehicleType(request.getVehicleType());
        profile.setVehicleNumber(request.getVehicleNumber());
        profile.setVerificationStatus(VerificationStatus.PENDING);
        return profileRepository.save(profile);
    }

    @Transactional(readOnly = true)
    public DeliveryPartnerProfile getMine(UUID userId) {
        return profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery partner profile not found"));
    }
}