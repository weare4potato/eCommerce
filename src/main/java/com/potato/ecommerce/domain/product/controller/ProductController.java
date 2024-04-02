package com.potato.ecommerce.domain.product.controller;

import com.potato.ecommerce.domain.category.entity.CategoryEntity;
import com.potato.ecommerce.domain.category.repository.CategoryRepository;
import com.potato.ecommerce.domain.product.dto.ProductRequest;
import com.potato.ecommerce.domain.product.dto.ProductResponse;
import com.potato.ecommerce.domain.product.entity.ProductEntity;
import com.potato.ecommerce.domain.product.service.ProductService;
import com.potato.ecommerce.domain.store.entity.StoreEntity;
import com.potato.ecommerce.domain.store.repository.StoreRepository;
import com.potato.ecommerce.global.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
        @RequestBody ProductRequest requestDto,
        HttpServletRequest request) {

        String token = jwtUtil.getTokenFromRequest(request);
        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String sellerBusinessNumber = jwtUtil.getUserInfoFromToken(token).getSubject();
        ProductResponse response = productService.createProduct(sellerBusinessNumber, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
