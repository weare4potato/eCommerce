package com.potato.ecommerce.domain.store.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
public class StoreResponse {

    private String name;
    private String description;
    private String phone;
    private String businessNumber;

    @Builder
    public StoreResponse(String name, String description, String phone, String businessNumber) {
        this.name = name;
        this.description = description;
        this.phone = phone;
        this.businessNumber = businessNumber;
    }
}
