package com.potato.ecommerce.domain.revenue.model;

import com.potato.ecommerce.domain.revenue.entity.RevenueEntity;
import lombok.Getter;

@Getter
public class Revenue {

    private Long id;
    private final String number;
    private boolean isUsed;

    public Revenue(Long number) {
        this.number = String.format("%010d", number);
    }

    public Revenue(Long id, String number, boolean isUsed) {
        this.id = id;
        this.number = number;
        this.isUsed = isUsed;
    }


    public void use() {
        isUsed = true;
    }

    public boolean isUsedChecking() {
        return isUsed;
    }
}
