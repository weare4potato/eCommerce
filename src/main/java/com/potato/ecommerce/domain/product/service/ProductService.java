package com.potato.ecommerce.domain.product.service;

import com.potato.ecommerce.domain.category.entity.CategoryEntity;
import com.potato.ecommerce.domain.category.repository.CategoryRepository;
import com.potato.ecommerce.domain.product.dto.ProductRequest;
import com.potato.ecommerce.domain.product.dto.ProductResponse;
import com.potato.ecommerce.domain.product.entity.ProductEntity;
import com.potato.ecommerce.domain.product.repository.ProductRepository;
import com.potato.ecommerce.domain.store.entity.StoreEntity;
import com.potato.ecommerce.domain.store.repository.StoreRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public ProductResponse createProduct(String businessNumber, ProductRequest requestDto) {
        System.out.println("Requested categoryId: " + requestDto.getCategoryId());

        StoreEntity store = storeRepository.findByBusinessNumber(businessNumber)
            .orElseThrow(() -> new IllegalArgumentException("상점을 찾을 수 없습니다."));
        CategoryEntity category = categoryRepository.findById(requestDto.getCategoryId())
            .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));

        ProductEntity productEntity = ProductEntity.builder()
            .store(store)
            .category(category)
            .name(requestDto.getName())
            .description(requestDto.getDescription())
            .price(requestDto.getPrice())
            .stock(requestDto.getStock())
            .isDelete(false)
            .createdAt(LocalDateTime.now())
            .build();

        productRepository.save(productEntity);

        return new ProductResponse(productEntity);
    }

}
