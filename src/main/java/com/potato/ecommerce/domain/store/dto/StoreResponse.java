package com.potato.ecommerce.domain.store.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
public class StoreResponse {

    private String email;
    private String name;
    private String description;
    private String phone;
    private String businessNumber;

    @Builder
    public StoreResponse(String email, String name, String description, String phone, String businessNumber) {
        this.email = email;
        this.name = name;
        this.description = description;
        this.phone = phone;
        this.businessNumber = businessNumber;
    }
}
