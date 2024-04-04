package com.potato.ecommerce.domain.product.model;

import com.potato.ecommerce.domain.category.entity.CategoryEntity;
import com.potato.ecommerce.domain.product.dto.ProductUpdateRequest;
import com.potato.ecommerce.domain.product.entity.ProductEntity;
import com.potato.ecommerce.domain.store.entity.StoreEntity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Product {

    private Long id;
    private StoreEntity store;
    private CategoryEntity category;
    private Long categoryId;
    private String name;
    private String description;
    private Integer price;
    private Integer stock;
    private Boolean isDeleted;
    private LocalDateTime createdAt;

    public ProductEntity toEntity() {
        return ProductEntity.builder()
            .id(id)
            .store(store)
            .category(category)
            .name(name)
            .description(description)
            .price(price)
            .stock(stock)
            .isDeleted(isDeleted)
            .build();
    }

    public static Product fromEntity(ProductEntity entity) {
        return Product.builder()
            .id(entity.getId())
            .store(entity.getStore())
            .category(entity.getCategory())
            .name(entity.getName())
            .description(entity.getDescription())
            .price(entity.getPrice())
            .stock(entity.getStock())
            .isDeleted(entity.getIsDeleted())
            .build();
    }

    @Builder
    public Product(Long id, StoreEntity store, CategoryEntity category, String name,
        String description,
        Integer price, Integer stock, Boolean isDeleted, LocalDateTime createdAt) {
        this.id = id;
        this.store = store;
        this.category = category;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
    }

    public void updateFromRequest(ProductUpdateRequest updateRequest) {
        this.categoryId = updateRequest.getCategoryId();
        this.name = updateRequest.getProductName();
        this.description = updateRequest.getDescription();
        this.price = updateRequest.getPrice();
        this.stock = updateRequest.getStock();
    }

    public void updateCategory(CategoryEntity newCategory) {
        this.category = newCategory;
    }

}
