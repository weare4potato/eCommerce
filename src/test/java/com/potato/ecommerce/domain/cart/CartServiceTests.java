package com.potato.ecommerce.domain.cart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.potato.ecommerce.domain.cart.dto.CartInfo;
import com.potato.ecommerce.domain.cart.entity.CartEntity;
import com.potato.ecommerce.domain.cart.repository.CartJpaRepository;
import com.potato.ecommerce.domain.cart.service.CartService;
import com.potato.ecommerce.domain.member.MemberSteps;
import com.potato.ecommerce.domain.member.entity.MemberEntity;
import com.potato.ecommerce.domain.member.repository.MemberJpaRepository;
import com.potato.ecommerce.domain.product.ProductSteps;
import com.potato.ecommerce.domain.product.entity.ProductEntity;
import com.potato.ecommerce.domain.product.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class CartServiceTests {

    @InjectMocks
    private CartService cartService;

    @Mock
    private CartJpaRepository cartJpaRepository;

    @Mock
    private MemberJpaRepository memberJpaRepository;

    @Mock
    private ProductRepository productRepository;

    @Spy
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void Cart_add() {
        // Arrange
        String email = "test@email.com";
        Long productId = 1L;
        Integer quantity = 5;
        final MemberEntity member = MemberSteps.createMember(passwordEncoder);
        final ProductEntity product = ProductSteps.createProduct();
        final CartEntity cart = CartSteps.createCartWithMemberAndProduct(member, product, quantity);

        given(memberJpaRepository.findByEmail(email)).willReturn(Optional.of(member));
        given(productRepository.findById(productId)).willReturn(Optional.of(product));
        given(cartJpaRepository.save(any(CartEntity.class))).willReturn(cart);

        // Act
        final CartInfo response = cartService.addCart(email, productId, quantity);

        // Assert
        assertThat(response.getQuantity()).isEqualTo(quantity);
    }

    @Test
    void Cart_update() {
        // Arrange
        String email = "test@email.com";
        Long cartId = 1L;
        Integer quantity = 10;
        final CartEntity cart = CartSteps.createCart();

        given(cartJpaRepository.findByMemberEmailAndId(email, cartId)).willReturn(Optional.of(cart));

        // Act
        final CartInfo response = cartService.updateCart(email, cartId, quantity);

        // Assert
        assertThat(response.getQuantity()).isEqualTo(quantity);
    }

    @Test
    void Cart_delete() {
        // Arrange
        String email = "test@email.com";
        Long cartId = 1L;
        final CartEntity cart = CartSteps.createCart();

        given(cartJpaRepository.findByMemberEmailAndId(email, cartId)).willReturn(Optional.of(cart));

        // Act
        cartService.deleteCart(email, cartId);

        // Assert
        verify(cartJpaRepository, times(1)).delete(cart);
    }

    @Test
    void Cart_find_all() {
        // Arrange
        String email = "test@email.com";
        final CartEntity cart1 = CartSteps.createCart();
        final CartEntity cart2 = CartSteps.createCart();
        List<CartEntity> cartList = new ArrayList<>();
        cartList.add(cart1);
        cartList.add(cart2);

        given(cartJpaRepository.findAllByMember_Email(email)).willReturn(cartList);

        // Act
        final List<CartInfo> response = cartService.getCarts(email);

        // Assert
        assertThat(response.size()).isEqualTo(2);
        assertThat(response.get(0).getQuantity()).isEqualTo(5);
    }

}
