package com.potato.ecommerce.domain.product.dto;

import com.potato.ecommerce.domain.product.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductSimpleResponse {

    String name;
    Integer price;

    public ProductSimpleResponse(ProductEntity productEntity) {
        this.name = productEntity.getName();
        this.price = productEntity.getPrice();
    }
}
