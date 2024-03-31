package com.potato.ecommerce.domain.revenue.entity;

import com.potato.ecommerce.domain.revenue.model.Revenue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RevenueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    private RevenueEntity(String number) {
        this.number = number;
    }

    public static RevenueEntity fromModel(Revenue revenue){
        return new RevenueEntity(revenue.getNumber());
    }

    public Revenue toModel(){
        return new Revenue(id, number);
    }
}
