package com.potato.ecommerce.domain.product.controller;

import com.potato.ecommerce.domain.product.dto.ProductDetailResponse;
import com.potato.ecommerce.domain.product.dto.ProductRequest;
import com.potato.ecommerce.domain.product.dto.ProductResponse;
import com.potato.ecommerce.domain.product.dto.ProductSimpleResponse;
import com.potato.ecommerce.domain.product.dto.ProductUpdateRequest;
import com.potato.ecommerce.domain.product.dto.ShopProductResponse;
import com.potato.ecommerce.domain.product.service.ProductService;
import com.potato.ecommerce.domain.s3.service.S3Service;
import com.potato.ecommerce.global.util.RestPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
@Tag(name = "Product API")
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;
    private final S3Service s3Service;

    @PostMapping("/products")
    @Operation(summary = "상품 등록")
    public ResponseEntity<ProductResponse> createProduct(
        @Valid ProductRequest requestDto, HttpServletRequest request
    ) {
        String subject = (String) request.getAttribute("subject");

        String url = s3Service.upload(requestDto.getImage());
        ProductResponse productResponse = productService.createProduct(subject, requestDto, url);
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponse);
    }

    @GetMapping("/products/all")
    @Operation(summary = "모든 상품 조회")
    public ResponseEntity<RestPage<ProductSimpleResponse>> getAllProducts(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size) {

        RestPage<ProductSimpleResponse> products = productService.findAllProducts(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/products/details/{productId}")
    @Operation(summary = "상품 상세 조회")
    public ResponseEntity<ProductDetailResponse> getProductDetail(@PathVariable Long productId) {
        ProductDetailResponse productDetail = productService.findProductDetail(productId);
        return ResponseEntity.status(HttpStatus.OK).body(productDetail);
    }

    @GetMapping("/products/shops/{shopId}")
    @Operation(summary = "상점별 상품 조회")
    public ResponseEntity<RestPage<ShopProductResponse>> getProductsByShop(
        @PathVariable Long shopId,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size) {

        RestPage<ShopProductResponse> products = productService.findProductsByShopId(shopId, page,
            size);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/products/search")
    @Operation(summary = "상품 검색 조회")
    public ResponseEntity<RestPage<ProductSimpleResponse>> getProductBySearch(
        @RequestParam String keyword,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size) {

        RestPage<ProductSimpleResponse> products =
            productService.findAllByContainingKeyword(keyword, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/products/categories")
    @Operation(summary = "카테고리별 상품 조회")
    public ResponseEntity<RestPage<ProductSimpleResponse>> findProductsByCategory(
        @RequestParam("productCategoryId") Long productCategoryId,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size) {

        RestPage<ProductSimpleResponse> products = productService.findProductsByCategoryId(
            productCategoryId, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @PutMapping("/products/{productId}")
    @Operation(summary = "상품 수정")
    public ResponseEntity<ProductResponse> updateProduct(
        @PathVariable Long productId,
        @Valid @RequestBody ProductUpdateRequest updateRequest) {

        ProductResponse updatedProduct = productService.updateProduct(productId, updateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

    @DeleteMapping("/products/{productId}")
    @Operation(summary = "상품 삭제")
    public ResponseEntity<Void> deleteProduct(
        @PathVariable Long productId) {

        productService.softDeleteProduct(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
