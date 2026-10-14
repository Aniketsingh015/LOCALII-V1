package com.localii.backend.identity.repository;

import com.localii.backend.identity.entity.VendorProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VendorProfileRepository extends JpaRepository<VendorProfile, UUID> {

    boolean existsByUserId(UUID userId);

    Optional<VendorProfile> findByUserId(UUID userId);
}