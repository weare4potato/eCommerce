package com.potato.ecommerce.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductListResponse {

    private Long productId;
    private String name;
    private Long productCategoryId;
    private Long price;
    private Integer stock;
}
