package com.potato.ecommerce.domain.order.dto;

import com.potato.ecommerce.domain.order.model.History;
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

    public static HistoryInfo fromEntity(History history) {
        return HistoryInfo.builder()
            .id(history.getId())
            .product(history.getProduct().toModel())
            .quantity(history.getQuantity())
            .build();
    }
}
