package com.potato.ecommerce.domain.product.controller;

import static com.potato.ecommerce.domain.product.message.ProductMessage.ALL_PRODUCT_LIST;
import static com.potato.ecommerce.domain.product.message.ProductMessage.PRODUCT_API;
import static com.potato.ecommerce.domain.product.message.ProductMessage.PRODUCT_CREATE;
import static com.potato.ecommerce.domain.product.message.ProductMessage.PRODUCT_DELETE;
import static com.potato.ecommerce.domain.product.message.ProductMessage.PRODUCT_DELETE_SUCCESS;
import static com.potato.ecommerce.domain.product.message.ProductMessage.PRODUCT_DETAIL;
import static com.potato.ecommerce.domain.product.message.ProductMessage.PRODUCT_LIST_BY_CATEGORY;
import static com.potato.ecommerce.domain.product.message.ProductMessage.PRODUCT_LIST_BY_STORE;
import static com.potato.ecommerce.domain.product.message.ProductMessage.PRODUCT_UPDATE;
import static com.potato.ecommerce.domain.store.message.StoreMessage.STORE_API;

import com.potato.ecommerce.domain.product.dto.ProductDetailResponse;
import com.potato.ecommerce.domain.product.dto.ProductRequest;
import com.potato.ecommerce.domain.product.dto.ProductResponse;
import com.potato.ecommerce.domain.product.dto.ProductSimpleResponse;
import com.potato.ecommerce.domain.product.dto.ProductUpdateRequest;
import com.potato.ecommerce.domain.product.dto.ShopProductResponse;
import com.potato.ecommerce.domain.product.service.ProductService;
import com.potato.ecommerce.global.jwt.JwtUtil;
import com.potato.ecommerce.global.util.RestPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = PRODUCT_API)
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;
    private final JwtUtil jwtUtil;

    @PostMapping("/products")
    @Operation(summary = PRODUCT_CREATE)
    public ResponseEntity<Void> createProduct(
        @Valid @RequestBody ProductRequest requestDto, HttpServletRequest request
    ) {
        String subject = (String) request.getAttribute("subject");

        productService.createProduct(subject, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/products/all")
    @Operation(summary = ALL_PRODUCT_LIST)
    public ResponseEntity<RestPage<ProductSimpleResponse>> getAllProducts(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size) {

        RestPage<ProductSimpleResponse> products = productService.findAllProducts(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/products/details/{productId}")
    @Operation(summary = PRODUCT_DETAIL)
    public ResponseEntity<ProductDetailResponse> getProductDetail(@PathVariable Long productId) {
        ProductDetailResponse productDetail = productService.findProductDetail(productId);
        return ResponseEntity.status(HttpStatus.OK).body(productDetail);
    }

    @GetMapping("/shops/{shopId}/shop-products")
    @Operation(summary = PRODUCT_LIST_BY_STORE)
    public ResponseEntity<RestPage<ShopProductResponse>> getProductsByShop(
        @PathVariable Long shopId,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size) {

        RestPage<ShopProductResponse> products = productService.findProductsByShopId(shopId, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/products/categories")
    @Operation(summary = PRODUCT_LIST_BY_CATEGORY)
    public ResponseEntity<RestPage<ProductSimpleResponse>> getProductsByCategory(
        @RequestParam("categoryId") Long categoryId,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size) {

        RestPage<ProductSimpleResponse> products = productService.findProductsByCategoryId(categoryId, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @PutMapping("/products/{productId}")
    @Operation(summary = PRODUCT_UPDATE)
    public ResponseEntity<ProductResponse> updateProduct(
        @PathVariable Long productId,
        @Valid @RequestBody ProductUpdateRequest updateRequest,
        HttpServletRequest request) {

        String subject = (String) request.getAttribute("subject");

        ProductResponse updatedProduct = productService.updateProduct(productId, updateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

    @DeleteMapping("/products/{productId}")
    @Operation(summary = PRODUCT_DELETE)
    public ResponseEntity<?> deleteProduct(
        @PathVariable Long productId,
        HttpServletRequest request) {

        String subject = (String) request.getAttribute("subject");

        productService.softDeleteProduct(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(PRODUCT_DELETE_SUCCESS);
    }

}
