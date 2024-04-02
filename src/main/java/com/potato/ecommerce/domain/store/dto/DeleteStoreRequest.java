package com.potato.ecommerce.domain.store.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class DeleteStoreRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 15)
    @Pattern(regexp = "^[a-zA-Z0-9.!@#$]*$")
    private String password;

    @NotBlank
    @Pattern(regexp = "^[0-9]{10}+$")
    private String businessNumber;
}
