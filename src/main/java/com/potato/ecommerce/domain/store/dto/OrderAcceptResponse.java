package com.potato.ecommerce.domain.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderAcceptResponse {
    private String message;
    private String description;
}
