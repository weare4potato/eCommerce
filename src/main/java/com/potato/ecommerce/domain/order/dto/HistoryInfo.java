package com.potato.ecommerce.domain.order.dto;

import com.potato.ecommerce.domain.order.entity.HistoryEntity;
import com.potato.ecommerce.domain.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryInfo {
    private Long id;
    private Product product;
    private Integer quantity;

    public static HistoryInfo fromEntity(HistoryEntity entity){
        return HistoryInfo.builder()
            .id(entity.getId())
            .product(entity.getProduct().toModel())
            .quantity(entity.getQuantity())
            .build();
    }
}
