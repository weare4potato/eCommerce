package com.potato.ecommerce.domain.receiver.dto;

import com.potato.ecommerce.domain.receiver.entity.ReceiverEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceiverForm {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Pattern(regexp = "^01(0)([0-9]{4})([0-9]{4})$")
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


    public static ReceiverForm fromEntity(ReceiverEntity entity) {
        return ReceiverForm.builder()
            .name(entity.getName())
            .phone(entity.getPhone())
            .addressName(entity.getAddressName())
            .city(entity.getCity())
            .street(entity.getStreet())
            .zipcode(entity.getZipcode())
            .detail(entity.getDetail())
            .build();
    }
}
