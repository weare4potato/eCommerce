package com.potato.ecommerce.domain.order.dto;

import com.potato.ecommerce.domain.order.entity.HistoryEntity;
import com.potato.ecommerce.domain.product.entity.ProductEntity;
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
    private ProductEntity product;
    private Integer quantity;
    private Long orderPrice;

    public static HistoryInfo fromEntity(HistoryEntity historyEntity) {
        return HistoryInfo.builder()
            .id(historyEntity.getId())
            .product(historyEntity.getProduct())
            .quantity(historyEntity.getQuantity())
            .orderPrice(historyEntity.getOrderPrice())
            .build();
    }
}
