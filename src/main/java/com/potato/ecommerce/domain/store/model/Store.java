package com.potato.ecommerce.domain.store.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Store {

    private final Long id;
    private final String email;
    private final String password;
    private final String name;
    private final String description;
    private final String phone;
    private final String licenseNumber;
    private final LocalDateTime createdAt;

    @Builder
    public Store(Long id, String email, String password, String name, String description,
        String phone,
        String licenseNumber, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.description = description;
        this.phone = phone;
        this.licenseNumber = licenseNumber;
        this.createdAt = createdAt;
    }
}
