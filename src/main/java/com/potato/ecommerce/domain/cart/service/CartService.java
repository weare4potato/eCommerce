package com.potato.ecommerce.domain.cart.service;

import com.potato.ecommerce.domain.cart.dto.CartInfo;
import com.potato.ecommerce.domain.cart.entity.CartEntity;
import com.potato.ecommerce.domain.cart.repository.CartJpaRepository;
import com.potato.ecommerce.domain.member.entity.MemberEntity;
import com.potato.ecommerce.domain.member.repository.MemberJpaRepository;
import com.potato.ecommerce.domain.product.entity.ProductEntity;
import com.potato.ecommerce.domain.product.repository.ProductRepository;
import com.potato.ecommerce.global.exception.ExceptionMessage;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartJpaRepository cartJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final ProductRepository productRepository;

    public CartInfo addCart(
        Long memberId,
        Long productId,
        Integer quantity
    ) {
        MemberEntity memberEntity = memberJpaRepository.findById(memberId)
            .orElseThrow(() -> new EntityNotFoundException(
                ExceptionMessage.MEMBER_NOT_FOUND.toString()));

        ProductEntity productEntity = productRepository.findById(productId)
            .orElseThrow(() -> new EntityNotFoundException(
                ExceptionMessage.PRODUCT_NOT_FOUND.toString()));

        CartEntity cartEntity =
            cartJpaRepository.
                findByMember_IdAndProduct_Id(memberEntity.getId(), productEntity.getId())
                .orElseGet(() -> CartEntity.builder()
                    .member(memberEntity)
                    .product(productEntity)
                    .quantity(quantity).build());

        if (Objects.nonNull(cartEntity.getId())) {
            cartEntity.addQuantity(quantity);

        }

        cartJpaRepository.save(cartEntity);

        return CartInfo.fromEntity(cartEntity);
    }

    public CartInfo updateCart(
        Long memberId,
        Long cartId,
        Integer quantity
    ) {
        CartEntity cartEntity = cartJpaRepository.findByMemberIdAndId(memberId, cartId)
            .orElseThrow(() -> new EntityNotFoundException(
                ExceptionMessage.CART_NOT_FOUND.toString()));

        cartEntity.updateQuantity(quantity);

        return CartInfo.fromEntity(cartEntity);
    }

    public void deleteCart(
        String email,
        Long cartId
    ) {
        CartEntity cartEntity = cartJpaRepository.findByMemberEmailAndId(email, cartId)
            .orElseThrow(() -> new EntityNotFoundException(
                ExceptionMessage.CART_NOT_FOUND.toString()));

        cartJpaRepository.delete(cartEntity);
    }

    @Transactional(readOnly = true)
    public List<CartInfo> getCarts(String email) {

        return cartJpaRepository.findAllByMember_Email(email).stream().map(CartInfo::fromEntity)
            .toList();
    }
}
