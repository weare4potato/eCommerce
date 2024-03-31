package com.potato.ecommerce.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SignUp {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 2, max = 12)
    private String username;

    @NotBlank
    @Size(min = 8, max = 15)
    @Pattern(regexp = "^[a-zA-Z0-9.!@#$]*$")
    private String password;

    @NotBlank
    @Pattern(regexp = "^01(0)-([0-9]{4})-([0-9]{4})$")
    private String phone;
}
