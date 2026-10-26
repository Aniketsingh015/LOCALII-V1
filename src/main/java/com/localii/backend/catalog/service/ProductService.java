package com.localii.backend.catalog.service;

import com.localii.backend.catalog.dto.CreateProductRequest;
import com.localii.backend.catalog.dto.ProductResponse;
import com.localii.backend.catalog.dto.UpdateProductRequest;
import com.localii.backend.catalog.entity.Category;
import com.localii.backend.catalog.entity.Product;
import com.localii.backend.catalog.repository.CategoryRepository;
import com.localii.backend.catalog.repository.ProductRepository;
import com.localii.backend.catalog.specification.ProductSpecs;
import com.localii.backend.common.exception.DuplicateResourceException;
import com.localii.backend.common.exception.ForbiddenOperationException;
import com.localii.backend.common.exception.ResourceNotFoundException;
import com.localii.backend.identity.entity.VendorProfile;
import com.localii.backend.identity.entity.VerificationStatus;
import com.localii.backend.identity.repository.VendorProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final VendorProfileRepository vendorProfileRepository;
    private final CategoryRepository categoryRepository;

    // ---------- vendor side ----------

    @Transactional
    public ProductResponse create(UUID userId, CreateProductRequest request) {
        VendorProfile vendor = vendorProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Create your shop profile first"));

        if (vendor.getVerificationStatus() != VerificationStatus.APPROVED) {
            throw new ForbiddenOperationException("Your shop is not approved yet");
        }

        Category category = findCategory(request.getCategoryId());

        Product product = new Product();
        product.setVendor(vendor);
        product.setCategory(category);
        product.setName(request.getName().trim());
        product.setDescription(request.getDescription().trim());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.replaceImages(request.getImageUrls());

        return new ProductResponse(productRepository.save(product));
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> listMine(UUID userId) {
        return productRepository.findByVendorUserIdAndActiveTrueOrderByCreatedAtDesc(userId)
                .stream().map(ProductResponse::new).toList();
    }

    @Transactional
    public ProductResponse update(UUID userId, UUID productId, UpdateProductRequest request) {
        Product product = findOwned(userId, productId);

        product.setCategory(findCategory(request.getCategoryId()));
        product.setName(request.getName().trim());
        product.setDescription(request.getDescription().trim());
        product.setPrice(request.getPrice());
        if (request.getImageUrls() != null) {
            product.replaceImages(request.getImageUrls());
        }
        return new ProductResponse(product);   // saved automatically (dirty checking)
    }

    @Transactional
    public ProductResponse updateStock(UUID userId, UUID productId, int stock) {
        Product product = findOwned(userId, productId);
        product.setStock(stock);
        return new ProductResponse(product);
    }

    @Transactional
    public void remove(UUID userId, UUID productId) {
        findOwned(userId, productId).setActive(false);
    }

    // ---------- public side ----------

    @Transactional(readOnly = true)
    public List<ProductResponse> browse(String city, UUID categoryId, String q, int page, int size) {
        Specification<Product> spec = ProductSpecs.visible();
        if (StringUtils.hasText(city)) {
            spec = spec.and(ProductSpecs.inCity(city.trim()));
        }
        if (categoryId != null) {
            spec = spec.and(ProductSpecs.inCategory(categoryId));
        }
        if (StringUtils.hasText(q)) {
            spec = spec.and(ProductSpecs.nameContains(q.trim()));
        }

        Pageable pageable = PageRequest.of(
                Math.max(page, 0),
                Math.min(Math.max(size, 1), 50),
                Sort.by("createdAt").descending());

        return productRepository.findAll(spec, pageable)
                .stream().map(ProductResponse::new).toList();
    }

    @Transactional(readOnly = true)
    public ProductResponse getPublic(UUID productId) {
        Product product = productRepository.findById(productId)
                .filter(p -> p.isActive()
                        && p.getVendor().getVerificationStatus() == VerificationStatus.APPROVED)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return new ProductResponse(product);
    }

    // ---------- admin side ----------

    @Transactional
    public ProductResponse takedown(UUID productId, String reason) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        if (!product.isActive()) {
            throw new DuplicateResourceException("Product already removed");
        }
        product.setActive(false);
        product.setRemovalReason(reason.trim());
        return new ProductResponse(product);
    }

    // ---------- helpers ----------

    private Product findOwned(UUID userId, UUID productId) {
        return productRepository.findByIdAndVendorUserIdAndActiveTrue(productId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    private Category findCategory(UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }
}