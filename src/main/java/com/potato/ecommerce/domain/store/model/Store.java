package com.potato.ecommerce.domain.store.model;

import com.potato.ecommerce.domain.store.entity.StoreEntity;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Store {

    private final Long id;
    private final String email;
    private final String password;
    private String name;
    private String description;
    private String phone;
    private final String businessNumber;
    private final LocalDateTime createdAt;

    @Builder
    public Store(Long id, String email, String password, String name, String description,
        String phone,
        String businessNumber, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.description = description;
        this.phone = phone;
        this.businessNumber = businessNumber;
        this.createdAt = createdAt;
    }

    public StoreEntity toEntity() {
        return StoreEntity.builder()
            .id(id)
            .email(email)
            .password(password)
            .name(name)
            .description(description)
            .phone(phone)
            .businessNumber(businessNumber)
            .createdAt(createdAt)
            .build();
    }

    public void update(String name, String description, String phone) {
        this.name = name;
        this.description = description;
        this.phone = phone;
    }
}
