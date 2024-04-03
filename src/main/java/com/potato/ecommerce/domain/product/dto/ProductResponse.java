package com.potato.ecommerce.domain.product.dto;

import com.potato.ecommerce.domain.product.entity.ProductEntity;
import com.potato.ecommerce.domain.product.model.Product;
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

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.storeId = product.getStore().getId();
        this.categoryId = product.getCategory().getId();
    }
}

