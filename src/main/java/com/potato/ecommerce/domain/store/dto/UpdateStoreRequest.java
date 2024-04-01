package com.potato.ecommerce.domain.store.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateStoreRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private String phone;
}
