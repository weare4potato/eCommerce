package com.potato.ecommerce.domain.product.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ProductUpdateRequest {

    private Long categoryId;

    @Size(max = 150)
    private String productName;

    private String description;

    private int price;

    private int stock;

}
