package com.localii.backend.catalog.repository;
import com.localii.backend.catalog.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product,UUID> {

    List<Product>findByVendorUserIdAndActiveTrueOrderByCreatedAtDesc(UUID userId);
    Optional<Product> findByIdAndVendorUserIdAndActiveTrue(UUID id, UUID userId);
}