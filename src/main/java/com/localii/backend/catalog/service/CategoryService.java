package com.localii.backend.catalog.service;

import com.localii.backend.catalog.dto.CreateCategoryRequest;
import com.localii.backend.catalog.entity.Category;
import com.localii.backend.catalog.repository.CategoryRepository;
import com.localii.backend.common.exception.DuplicateResourceException;
import com.localii.backend.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public Category create(CreateCategoryRequest request) {
        String name = request.getName().trim();
        String slug = toSlug(name);

        if (categoryRepository.existsByName(name)) {
            throw new DuplicateResourceException("Category name already exists");
        }
        if (categoryRepository.existsBySlug(slug)) {
            throw new DuplicateResourceException("Category already exists");
        }

        Category category = new Category();
        category.setName(name);
        category.setSlug(slug);

        if (request.getParentId() != null) {
            Category parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent category not found"));
            category.setParent(parent);
        }

        return categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    public List<Category> listActive() {
        return categoryRepository.findByActiveTrueOrderByNameAsc();
    }

    private String toSlug(String name) {
        return name.toLowerCase()
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-|-$", "");
    }
}