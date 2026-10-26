package com.localii.backend.catalog.repository;

import com.localii.backend.catalog.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository
        extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {

    List<Product> findByVendorUserIdAndActiveTrueOrderByCreatedAtDesc(UUID userId);

    Optional<Product> findByIdAndVendorUserIdAndActiveTrue(UUID id, UUID userId);
}