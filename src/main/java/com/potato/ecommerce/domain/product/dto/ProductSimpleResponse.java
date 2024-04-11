package com.potato.ecommerce.domain.product.dto;

import com.potato.ecommerce.domain.product.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductSimpleResponse {

    String name;
    Integer price;

    public static ProductSimpleResponse of(ProductEntity productEntity) {
        return new ProductSimpleResponse(productEntity.getName(), productEntity.getPrice());
    }

}
