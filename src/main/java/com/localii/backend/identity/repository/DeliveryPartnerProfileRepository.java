package com.localii.backend.identity.repository;

import com.localii.backend.identity.entity.DeliveryPartnerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryPartnerProfileRepository extends JpaRepository<DeliveryPartnerProfile, UUID> {
    boolean existsByUserId(UUID userId);
    Optional<DeliveryPartnerProfile> findByUserId(UUID userId);
}