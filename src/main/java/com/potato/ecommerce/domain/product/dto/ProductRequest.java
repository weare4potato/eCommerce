package com.potato.ecommerce.domain.product.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductRequest {

    private Long categoryId;

    @Size(max = 150)
    private String name;

    private String description;

    private Integer price;

    private Integer stock;

}
