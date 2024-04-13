package com.potato.ecommerce.domain.product.dto;

import com.potato.ecommerce.domain.product.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductSimpleResponse {

    Long id;
    String name;
    Long price;

    public static ProductSimpleResponse of(ProductEntity productEntity) {
        return new ProductSimpleResponse(productEntity.getId(), productEntity.getName(), productEntity.getPrice());
    }

}
