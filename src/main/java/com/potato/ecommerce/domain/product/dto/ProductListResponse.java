package com.potato.ecommerce.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductListResponse {

    private String name;
    private Long productCategory;
    private Long price;
    private Integer stock;
}
