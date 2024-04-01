package com.potato.ecommerce.domain.category.controller;

import com.potato.ecommerce.domain.category.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/oneDepth")
    public ResponseEntity<List<String>> getAllOneDepthCategories() {
        List<String> oneDepthCategories = categoryService.getAllOneDepthCategories();
        return ResponseEntity.ok(oneDepthCategories);
    }

    @GetMapping("/twoDepth")
    public ResponseEntity<List<String>> getAllTwoDepthCategories() {
        List<String> twoDepthCategories = categoryService.getAllTwoDepthCategories();
        return ResponseEntity.ok(twoDepthCategories);
    }


}
