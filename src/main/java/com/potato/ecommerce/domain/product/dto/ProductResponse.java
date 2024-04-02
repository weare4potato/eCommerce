package com.potato.ecommerce.domain.product.dto;

import com.potato.ecommerce.domain.product.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductResponse {

    private final Long id;
    private final String name;
    private final String description;
    private final Integer price;
    private final Integer stock;
    private final Long storeId;
    private final Long categoryId;

    public ProductResponse(ProductEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
        this.stock = entity.getStock();
        this.storeId = entity.getStore().getId();
        this.categoryId = entity.getCategory().getId();
    }
}

