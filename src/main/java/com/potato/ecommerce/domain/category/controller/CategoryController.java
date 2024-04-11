package com.potato.ecommerce.domain.category.controller;

import com.potato.ecommerce.domain.category.dto.OneDepthDto;
import com.potato.ecommerce.domain.category.dto.ThreeDepthDto;
import com.potato.ecommerce.domain.category.dto.TwoDepthDto;
import com.potato.ecommerce.domain.category.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Category API")
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/oneDepth")
    @Operation(summary = "대분류 카테고리 조회")
    public ResponseEntity<List<OneDepthDto>> getAllOneDepthCategories() {
        List<OneDepthDto> oneDepthList = categoryService.getAllOneDepths();
        return ResponseEntity.status(HttpStatus.OK).body(oneDepthList);
    }

    @GetMapping("/twoDepth/{oneDepthId}")
    @Operation(summary = "중분류 카테고리 조회")
    public ResponseEntity<List<TwoDepthDto>> getAllTwoDepthCategories(@PathVariable Long oneDepthId) {
        List<TwoDepthDto> twoDepthList = categoryService.getTwoDepthsByOneDepth(oneDepthId);
        return ResponseEntity.status(HttpStatus.OK).body(twoDepthList);
    }

    @GetMapping("/threeDepth/{twoDepthId}")
    @Operation(summary = "소분류 카테고리 조회")
    public ResponseEntity<List<ThreeDepthDto>> getThreeDepthsByTwoDepth(@PathVariable Long twoDepthId) {
        List<ThreeDepthDto> threeDepthList = categoryService.getThreeDepthsByTwoDepth(twoDepthId);
        return ResponseEntity.status(HttpStatus.OK).body(threeDepthList);
    }

}
