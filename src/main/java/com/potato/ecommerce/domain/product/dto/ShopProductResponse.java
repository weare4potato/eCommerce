package com.potato.ecommerce.domain.product.dto;

import com.potato.ecommerce.domain.product.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShopProductResponse {

    private Long id;
    private String name;
    private Long price;

    public static ShopProductResponse of(ProductEntity productEntity) {
        return new ShopProductResponse(productEntity.getId(), productEntity.getName(), productEntity.getPrice());
    }

}
