package com.potato.ecommerce.domain.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductRequest {

    private Long categoryId;

    @Size(max = 150)
    @NotBlank
    private String name;

    private String description;

    @NotNull
    private Long price;

    @NotNull
    private Integer stock;

}
