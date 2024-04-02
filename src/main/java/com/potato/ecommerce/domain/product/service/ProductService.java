package com.potato.ecommerce.domain.product.service;

import com.potato.ecommerce.domain.category.entity.CategoryEntity;
import com.potato.ecommerce.domain.category.repository.CategoryRepository;
import com.potato.ecommerce.domain.product.dto.ProductRequest;
import com.potato.ecommerce.domain.product.dto.ProductResponse;
import com.potato.ecommerce.domain.product.dto.ProductSimpleResponse;
import com.potato.ecommerce.domain.product.entity.ProductEntity;
import com.potato.ecommerce.domain.product.model.Product;
import com.potato.ecommerce.domain.product.repository.ProductRepository;
import com.potato.ecommerce.domain.store.entity.StoreEntity;
import com.potato.ecommerce.domain.store.model.Store;
import com.potato.ecommerce.domain.store.repository.StoreRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public ProductResponse createProduct(String businessNumber, ProductRequest requestDto) {

        Store store = storeRepository.findBySubject(businessNumber);

        CategoryEntity category = categoryRepository.findById(requestDto.getCategoryId())
            .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));

        Product product = Product.builder()
            .store(StoreEntity.fromModel(store))
            .category(category)
            .name(requestDto.getName())
            .description(requestDto.getDescription())
            .price(requestDto.getPrice())
            .stock(requestDto.getStock())
            .isDelete(false)
            .createdAt(LocalDateTime.now())
            .build();

        productRepository.save(ProductEntity.fromModel(product));

        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getStock(),
            product.getStore().getId(),
            product.getCategory().getId());
    }

    public List<ProductSimpleResponse> findAllProductSimples() {
        List<ProductEntity> productEntities = productRepository.findAll();
        return productEntities.stream()
            .map(entity -> new ProductSimpleResponse(entity.getName(), entity.getPrice()))
            .collect(Collectors.toList());
    }

//    @GetMapping
//    public ResponseEntity<List<ProductResponse>> getAllProducts() {
//        List<ProductResponse> productResponses = productService.findAllProducts();
//        return ResponseEntity.ok(productResponses);
//    }

}
