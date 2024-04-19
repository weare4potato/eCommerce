package com.potato.ecommerce.domain.payment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PayType {
    CARD("카드"), VIRTUAL_ACCOUNT("가상계좌");

    private final String name;
}
