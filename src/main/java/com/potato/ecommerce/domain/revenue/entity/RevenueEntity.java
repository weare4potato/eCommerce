package com.potato.ecommerce.domain.revenue.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "revenues")
public class RevenueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    private boolean isUsed;

    public RevenueEntity(Long number) {
        this.number = String.format("%010d", number);
    }

    public void use() {
        isUsed = true;
    }

    public boolean isUsedChecking() {
        return isUsed;
    }
}
