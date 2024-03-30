package com.potato.ecommerce.domain.store.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Store {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String description;
    private String phone;
    private String licenseNumber;
    private LocalDateTime createAt;


}
