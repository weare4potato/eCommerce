package com.potato.ecommerce.domain.product.dto;

import com.potato.ecommerce.domain.product.entity.ProductEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ProductResponse {

    private final Long id;
    private final String name;
    private final String description;
    private final Integer price;
    private final Integer stock;
    private final Long storeId;
    private final Long categoryId;

    public ProductResponse(ProductEntity productEntity) {
        this.id = productEntity.getId();
        this.name = productEntity.getName();
        this.description = productEntity.getDescription();
        this.price = productEntity.getPrice();
        this.stock = productEntity.getStock();
        this.storeId = productEntity.getStore().getId();
        this.categoryId = productEntity.getCategory().getId();
    }
}

