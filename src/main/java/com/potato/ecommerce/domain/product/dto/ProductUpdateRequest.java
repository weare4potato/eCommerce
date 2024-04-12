package com.potato.ecommerce.domain.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductUpdateRequest {

    private Long productCategoryId;

    @Size(max = 150)
    @NotBlank
    private String productName;

    private String description;

    @NotNull
    private Long price;

    @NotNull
    private int stock;

}
