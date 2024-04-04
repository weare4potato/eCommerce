package com.potato.ecommerce.domain.cart.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartRequest {

    private Long memberId;

    @NotNull(message = "상품 아이디는 필수 입력 값입니다.")
    private Long productId;

    @Min(value = 1, message = "최소 1개 이상 담아주세요")
    private Integer quantity;

    public CartRequest(
        Long memberId,
        Long productId,
        Integer quantity
    ) {
        this.memberId = memberId;
        this.productId = productId;
        this.quantity = quantity;
    }
}
