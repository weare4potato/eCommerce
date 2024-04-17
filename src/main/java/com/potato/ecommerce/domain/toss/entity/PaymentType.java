package com.potato.ecommerce.domain.toss.entity;

public enum PaymentType {
    CARD("카드"),
    CASH("현금"),
    POINT("포인트");

    private final String description;

    PaymentType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
