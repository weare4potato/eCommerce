package com.potato.ecommerce.domain.order.dto;

import com.potato.ecommerce.domain.order.entity.HistoryEntity;
import com.potato.ecommerce.domain.product.dto.ProductSimpleResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryInfo {

    private Long id;
    private ProductSimpleResponse product;
    private Integer quantity;
    private Long orderPrice;

    public static HistoryInfo fromEntity(HistoryEntity historyEntity, ProductSimpleResponse product) {
        return HistoryInfo.builder()
            .id(historyEntity.getId())
            .product(product)
            .quantity(historyEntity.getQuantity())
            .orderPrice(historyEntity.getOrderPrice())
            .build();
    }
}
