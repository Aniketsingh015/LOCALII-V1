package com.localii.backend.catalog.service;
import com.localii.backend.common.exception.DuplicateResourceException;
import java.util.List;
import com.localii.backend.catalog.dto.CreateCategoryRequest;
import com.localii.backend.catalog.dto.CategoryResponse;
import com.localii.backend.catalog.entity.Category;
import com.localii.backend.catalog.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor 

public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional 
    public CategoryResponse create(CreateCategoryRequest request){//we not take name as parameter here because request contain name
        String name = request.getName().trim();
        if(categoryRepository.existsByName(name)){
            throw new DuplicateResourceException("Category name already exists");
        }
        Category category=new Category();
        category.setName(name);

        return new CategoryResponse(categoryRepository.save(category));

    }

    @Transactional(readOnly=true)
    public List<CategoryResponse> list(){
        return categoryRepository.findAllByOrderByNameAsc().stream().map(CategoryResponse::new).toList();
        // stream puts the items on the belt one after another, map converts each Category into a CategoryResponse, and toList collects them into a list again.
    }
}
