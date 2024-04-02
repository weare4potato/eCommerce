package com.potato.ecommerce.domain.product.model;

import com.potato.ecommerce.domain.category.entity.CategoryEntity;
import com.potato.ecommerce.domain.product.entity.ProductEntity;
import com.potato.ecommerce.domain.store.entity.StoreEntity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Product {

    private Long id;
    private StoreEntity store;
    private CategoryEntity category;
    private String name;
    private String description;
    private Integer price;
    private Integer stock;
    private Boolean isDelete;
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
            .isDelete(isDelete)
            .build();
    }

    @Builder
    public Product(Long id, StoreEntity store, CategoryEntity category, String name,
        String description,
        Integer price, Integer stock, Boolean isDelete, LocalDateTime createdAt) {
        this.id = id;
        this.store = store;
        this.category = category;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.isDelete = isDelete;
        this.createdAt = createdAt;
    }
}
