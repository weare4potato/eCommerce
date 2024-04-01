package com.potato.ecommerce.domain.category.service;

import com.potato.ecommerce.domain.category.entity.CategoryEntity;
import com.potato.ecommerce.domain.category.entity.CategoryType;
import com.potato.ecommerce.domain.category.repository.CategoryRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<String> getAllOneDepthCategories() {
        return categoryRepository.findDistinctOneDepth();
    }

}
