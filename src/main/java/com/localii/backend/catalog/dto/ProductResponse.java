package com.localii.backend.catalog.dto;

import com.localii.backend.catalog.entity.Product;
import com.localii.backend.catalog.entity.ProductImage;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
public class ProductResponse {
    private final UUID id;
    private final UUID vendorId;
    private final String shopName;
    private final String city;
    private final UUID categoryId;
    private final String categoryName;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final Integer stock;
    private final boolean inStock;
    private final List<String> imageUrls;
    private final Instant createdAt;

    public ProductResponse(Product p) {
        this.id = p.getId();
        this.vendorId = p.getVendor().getId();
        this.shopName = p.getVendor().getShopName();
        this.city = p.getVendor().getCity();
        this.categoryId = p.getCategory().getId();
        this.categoryName = p.getCategory().getName();
        this.name = p.getName();
        this.description = p.getDescription();
        this.price = p.getPrice();
        this.stock = p.getStock();
        this.inStock = p.getStock() > 0;
        this.imageUrls = p.getImages().stream().map(ProductImage::getImageUrl).toList();
        this.createdAt = p.getCreatedAt();
    }
}