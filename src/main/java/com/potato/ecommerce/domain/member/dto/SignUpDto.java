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
public class SignUpDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 2, max = 12, message = "최소 2글자 최대 12글자로 작성해주세요.")
    private String username;

    @NotBlank
    @Size(min = 8, max = 15,  message = "최소 8글자 최대 15글자로 작성해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9.!@#$]*$", message = "소문자, 대문자, 특수문자가 1개씩 포함되어야 합니다.")
    private String password;

    @NotBlank
    @Pattern(regexp = "^01(0)-([0-9]{4})-([0-9]{4})$", message = "전화번호 양식에 맞게 작성해주세요.")
    private String phone;
}
