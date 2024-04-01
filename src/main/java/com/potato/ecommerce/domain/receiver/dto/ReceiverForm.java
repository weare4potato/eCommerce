package com.potato.ecommerce.domain.receiver.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReceiverForm {

    @NotBlank
    private String name;

    @NotBlank
    @Pattern(regexp = "^01(0)-([0-9]{4})-([0-9]{4})$")
    private String phone;

    @Size(max = 12)
    private String addressName;

    @NotBlank
    private String city;

    @NotBlank
    private String street;

    @NotBlank
    private String zipcode;

    @NotBlank
    private String detail;
}
