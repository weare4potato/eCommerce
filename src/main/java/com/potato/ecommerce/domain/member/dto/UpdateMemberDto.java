package com.potato.ecommerce.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMemberDto {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Pattern(regexp = "^01(0)-([0-9]{4})-([0-9]{4})$")
    private String phone;
}
