package com.potato.ecommerce.domain.store.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
public class StoreResponse {

    private final Long id;
    private final String email;
    private final String name;
    private final String description;
    private final String phone;
    private final String businessNumber;

    @Builder
    public StoreResponse(Long id, String email, String name, String description, String phone,
        String businessNumber) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.description = description;
        this.phone = phone;
        this.businessNumber = businessNumber;
    }
}
