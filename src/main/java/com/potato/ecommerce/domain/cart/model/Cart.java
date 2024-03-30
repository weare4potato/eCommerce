package com.potato.ecommerce.domain.cart.model;

import com.potato.ecommerce.domain.cartProduct.entity.CartProductEntity;
import com.potato.ecommerce.domain.member.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class Cart {

    private Long id;
    private MemberEntity member;
    private CartProductEntity cartProduct;

    public Cart(Long id, MemberEntity member, CartProductEntity cartProduct) {
        this.id = id;
        this.member = member;
        this.cartProduct = cartProduct;
    }
}
