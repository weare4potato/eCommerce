package com.potato.ecommerce.domain.cart.model;

import com.potato.ecommerce.domain.member.entity.MemberEntity;
import com.potato.ecommerce.domain.product.entity.ProductEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class Cart {

    private Long id;
    private MemberEntity member;
    private ProductEntity product;
    private Integer quantity;

    public Cart(Long id,
        MemberEntity member,
        ProductEntity product,
        Integer quantity
    ) {
        this.id = id;
        this.member = member;
        this.product = product;
        this.quantity = quantity;
    }
}
