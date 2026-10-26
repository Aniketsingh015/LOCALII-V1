package com.localii.backend.catalog.controller;

import com.localii.backend.catalog.dto.*;
import com.localii.backend.catalog.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/vendors/products")
@RequiredArgsConstructor
@PreAuthorize("hasRole('VENDOR')")
public class VendorProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> create(Authentication auth,
                                                  @Valid @RequestBody CreateProductRequest request) {
        UUID userId = UUID.fromString(auth.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(userId, request));
    }

    @GetMapping
    public List<ProductResponse> listMine(Authentication auth) {
        return productService.listMine(UUID.fromString(auth.getName()));
    }

    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable UUID id, Authentication auth,
                                  @Valid @RequestBody UpdateProductRequest request) {
        return productService.update(UUID.fromString(auth.getName()), id, request);
    }

    @PatchMapping("/{id}/stock")
    public ProductResponse updateStock(@PathVariable UUID id, Authentication auth,
                                       @Valid @RequestBody UpdateStockRequest request) {
        return productService.updateStock(UUID.fromString(auth.getName()), id, request.getStock());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable UUID id, Authentication auth) {
        productService.remove(UUID.fromString(auth.getName()), id);
        return ResponseEntity.noContent().build();
    }
}