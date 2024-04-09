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

    public void addCart(
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

        // 어떤 멤버가 어떤 상품을 담았는지 확인
        CartEntity cartEntity =
            cartJpaRepository.findByMember_IdAndProduct_Id(memberEntity.getId(),
                productEntity.getId());

        // TODO : null 쓰지 않는 방식으로 수정
        // 상품을 처음으로 담을 경우 장바구니를 생성
        if (cartEntity == null) {
            cartEntity = CartEntity.builder()
                .member(memberEntity)
                .product(productEntity)
                .quantity(quantity)
                .build();

            cartJpaRepository.save(cartEntity);

            return;
        }
        // 상품이 이미 담겨있다면 수량을 업데이트
        cartEntity.addQuantity(quantity);
    }

    public void updateCart(
        Long memberId,
        Long cartId,
        Integer quantity
    ) {
        CartEntity cartEntity = cartJpaRepository.findByMemberIdAndId(memberId, cartId)
            .orElseThrow(() -> new EntityNotFoundException(
                ExceptionMessage.CART_NOT_FOUND.toString()));

        cartEntity.updateQuantity(quantity);
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
