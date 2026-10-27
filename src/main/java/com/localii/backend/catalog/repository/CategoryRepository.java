package com.localii.backend.catalog.repository;
import com.localii.backend.catalog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category,UUID> {
    boolean existsByName(String name);
    List<Category> findAllByOrderByNameAsc();
}
