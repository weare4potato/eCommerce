package com.potato.ecommerce.domain.cart;

import com.potato.ecommerce.domain.cart.entity.CartEntity;
import com.potato.ecommerce.domain.member.MemberSteps;
import com.potato.ecommerce.domain.member.entity.MemberEntity;
import com.potato.ecommerce.domain.product.ProductSteps;
import com.potato.ecommerce.domain.product.entity.ProductEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CartSteps {

    private static BCryptPasswordEncoder passwordEncoder;

    public static CartEntity createCartWithMemberAndProduct(final MemberEntity member,
        final ProductEntity product,
        final Integer quantity) {
        return CartEntity.builder()
            .member(member)
            .product(product)
            .quantity(quantity)
            .build();
    }

    public static CartEntity createCart() {
        return CartEntity.builder()
            .member(MemberSteps.createMember(passwordEncoder))
            .product(ProductSteps.createProduct())
            .quantity(5)
            .build();
    }
}
