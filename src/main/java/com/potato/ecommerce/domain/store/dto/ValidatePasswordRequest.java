package com.potato.ecommerce.domain.store.dto;

import static com.potato.ecommerce.global.exception.ExceptionMessage.PASSWORD_NOT_MATCH;

import jakarta.validation.ValidationException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidatePasswordRequest {

    @NotBlank
    @Size(min = 8, max = 15)
    @Pattern(regexp = "^[a-zA-Z0-9.!@#$]*$")
    private String firstPassword;

    @NotBlank
    @Size(min = 8, max = 15)
    @Pattern(regexp = "^[a-zA-Z0-9.!@#$]*$")
    private String secondPassword;

    public void validatePassword(){
        if(!firstPassword.equals(secondPassword)){
            throw new ValidationException(PASSWORD_NOT_MATCH.toString());
        }
    }
}
