package com.potato.ecommerce.domain.product.dto;

import lombok.Getter;

@Getter
public class ProductUpdateRequest {

    private Long categoryId;
    private String productName;
    private String description;
    private int price;
    private int stock;

}
