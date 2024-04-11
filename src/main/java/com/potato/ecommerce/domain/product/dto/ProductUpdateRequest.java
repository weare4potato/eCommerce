package com.potato.ecommerce.domain.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductUpdateRequest {

    private Long categoryId;

    @Size(max = 150)
    @NotBlank
    private String productName;

    private String description;

    @NotBlank
    private int price;

    @NotBlank
    private int stock;

}
