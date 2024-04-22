package com.potato.ecommerce.domain.product.dto;

import com.potato.ecommerce.domain.product.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductSimpleResponse {

    Long id;
    String name;
    Long price;
    String url;

    public ProductSimpleResponse(Long id, String name, Long price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static ProductSimpleResponse of(ProductEntity productEntity) {
        return new ProductSimpleResponse(productEntity.getId(), productEntity.getName(), productEntity.getPrice());
    }

}
