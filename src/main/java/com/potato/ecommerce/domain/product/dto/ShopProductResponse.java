package com.potato.ecommerce.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShopProductResponse {

    private String productName;
    private Integer price;

}
