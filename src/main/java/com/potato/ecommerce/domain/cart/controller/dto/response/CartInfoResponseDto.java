package com.potato.ecommerce.domain.cart.controller.dto.response;

import com.potato.ecommerce.domain.cart.dto.CartInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartInfoResponseDto {

    private Long cartId;
    private Long productId;
    private Integer quantity;

    public static CartInfoResponseDto from(CartInfo info) {
        return CartInfoResponseDto.builder()
            .cartId(info.getCartId())
            .productId(info.getProductId())
            .quantity(info.getQuantity())
            .build();
    }
}
