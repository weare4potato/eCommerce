package com.potato.ecommerce.domain.revenue.model;

import lombok.Getter;

@Getter
public class Revenue {

    private Long id;
    private final String number;

    public Revenue(Long number) {
        this.number = String.format("%09d", number);
    }

    public Revenue(Long id, String number) {
        this.id = id;
        this.number = number;
    }
}
