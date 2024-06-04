package com.potato.ecommerce.domain.cart;

import static org.assertj.core.api.Assertions.assertThat;

import com.potato.ecommerce.domain.cart.entity.CartEntity;
import com.potato.ecommerce.domain.member.MemberSteps;
import com.potato.ecommerce.domain.member.entity.MemberEntity;
import com.potato.ecommerce.domain.product.ProductSteps;
import com.potato.ecommerce.domain.product.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CartTests {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    void success_add_quantity() {
        // Arrange
        CartEntity cart = createCart();

        // Act
        cart.addQuantity(2);

        // Assert
        assertThat(cart.getQuantity()).isEqualTo(3);
    }

    private CartEntity createCart() {
        MemberEntity member = MemberSteps.createMember(passwordEncoder);
        ProductEntity product = ProductSteps.createProductWithStockAndPrice(5, 1000L);
        return CartEntity.builder()
            .member(member)
            .product(product)
            .quantity(1)
            .build();
    }
}
