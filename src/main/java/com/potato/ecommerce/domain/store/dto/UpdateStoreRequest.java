package com.potato.ecommerce.domain.store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateStoreRequest {

    @NotBlank
    @Size(max = 15)
    private String name;
    @NotBlank
    @Size(max = 50)
    private String description;
    @NotBlank
    @Pattern(regexp = "^01(0)([0-9]{4})([0-9]{4})$")
    private String phone;
}
