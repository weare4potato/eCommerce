package com.potato.ecommerce.domain.product.dto;

import com.potato.ecommerce.domain.product.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ShopProductResponse {

    private String productName;
    private Integer price;

    public ShopProductResponse(ProductEntity productEntity) {
        this.productName = productEntity.getName();
        this.price = productEntity.getPrice();
    }
}
