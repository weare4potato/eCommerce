package com.potato.ecommerce.domain.store.dto;

import lombok.Getter;

@Getter
public class ProductOfStoreResponse {
    private Long id;
    private String name;

    public ProductOfStoreResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
