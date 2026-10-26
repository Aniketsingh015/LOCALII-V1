package com.localii.backend.catalog.dto;

import com.localii.backend.catalog.entity.Category;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class CategoryResponse {
    private final UUID id;
    private final String name;
    private final String slug;
    private final UUID parentId;
    private final boolean active;
    private final Instant createdAt;

    public CategoryResponse(Category c) {
        this.id = c.getId();
        this.name = c.getName();
        this.slug = c.getSlug();
        this.parentId = (c.getParent() == null) ? null : c.getParent().getId();
        this.active = c.isActive();
        this.createdAt = c.getCreatedAt();
    }
}