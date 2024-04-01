package com.potato.ecommerce.domain.store.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ValidatePasswordRequest {
    @NotBlank
    private String firstPassword;
    @NotBlank
    private String secondPassword;
}
