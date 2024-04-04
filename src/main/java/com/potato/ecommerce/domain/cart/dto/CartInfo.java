package com.potato.ecommerce.domain.cart.dto;

import com.potato.ecommerce.domain.cart.entity.CartEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartInfo {

    private Long productId;
    private Integer quantity;

    public static CartInfo fromEntity(CartEntity entity) {
        return CartInfo.builder()
            .productId(entity.getProduct().getId())
            .quantity(entity.getQuantity())
            .build();
    }
}
