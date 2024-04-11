package com.potato.ecommerce.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductListResponse {

    private String name;
    private Long categoryId;
    private Integer price;
    private Integer stock;
}
