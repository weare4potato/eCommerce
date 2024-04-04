package com.potato.ecommerce.domain.product.controller;

import com.potato.ecommerce.domain.product.dto.ProductDetailResponse;
import com.potato.ecommerce.domain.product.dto.ProductRequest;
import com.potato.ecommerce.domain.product.dto.ProductResponse;
import com.potato.ecommerce.domain.product.dto.ProductSimpleResponse;
import com.potato.ecommerce.domain.product.dto.ProductUpdateRequest;
import com.potato.ecommerce.domain.product.dto.ShopProductResponse;
import com.potato.ecommerce.domain.product.service.ProductService;
import com.potato.ecommerce.global.jwt.JwtUtil;
import com.potato.ecommerce.global.util.RestPage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;
    private final JwtUtil jwtUtil;

    @PostMapping("/products")
    public ResponseEntity<Void> createProduct(
        @Valid @RequestBody ProductRequest requestDto, HttpServletRequest request
    ) {
        String subject = (String) request.getAttribute("subject");

        productService.createProduct(subject, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/products/all")
    public ResponseEntity<RestPage<ProductSimpleResponse>> getAllProducts(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size) {

        RestPage<ProductSimpleResponse> products = productService.findAllProducts(page, size);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/details/{productId}")
    public ResponseEntity<ProductDetailResponse> getProductDetail(@PathVariable Long productId) {
        ProductDetailResponse productDetail = productService.findProductDetail(productId);
        return ResponseEntity.ok(productDetail);
    }

    @GetMapping("/shops/{shopId}/shop-products")
    public ResponseEntity<RestPage<ShopProductResponse>> getProductsByShop(
        @PathVariable Long shopId,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size) {

        RestPage<ShopProductResponse> products = productService.findProductsByShopId(shopId, page, size);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/categories")
    public ResponseEntity<RestPage<ProductSimpleResponse>> getProductsByCategory(
        @RequestParam("categoryId") Long categoryId,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size) {

        RestPage<ProductSimpleResponse> products = productService.findProductsByCategoryId(categoryId, page, size);
        return ResponseEntity.ok(products);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(
        @PathVariable Long productId,
        @Valid @RequestBody ProductUpdateRequest updateRequest,
        HttpServletRequest request) {

        String subject = (String) request.getAttribute("subject");

        ProductResponse updatedProduct = productService.updateProduct(productId, updateRequest);
        return ResponseEntity.ok().body(updatedProduct);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(
        @PathVariable Long productId,
        HttpServletRequest request) {

        String subject = (String) request.getAttribute("subject");

        productService.softDeleteProduct(productId);
        return ResponseEntity.ok().body(Map.of("message", "상품 삭제 완료"));
    }

}
