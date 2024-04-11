package com.potato.ecommerce.domain.category.service;

import com.potato.ecommerce.domain.category.entity.CategoryEntity;
import com.potato.ecommerce.domain.category.entity.CategoryType;
import com.potato.ecommerce.domain.category.repository.CategoryRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<String> getAllOneDepthCategories() {
        return categoryRepository.findDistinctOneDepth().stream()
            .map(CategoryType::getDescription)
            .toList();
    }

    public List<String> getAllTwoDepthCategories() {
        return categoryRepository.findDistinctTwoDepth().stream()
            .map(CategoryType::getDescription)
            .toList();
    }

    public List<String> getAllThreeDepthCategories() {
        return categoryRepository.findDistinctThreeDepth().stream()
            .map(CategoryType::getDescription)
            .toList();
    }

}
