package com.potato.ecommerce.domain.cart.service;

import com.potato.ecommerce.domain.cart.dto.CartInfo;
import com.potato.ecommerce.domain.cart.entity.CartEntity;
import com.potato.ecommerce.domain.cart.repository.CartRepository;
import com.potato.ecommerce.domain.member.entity.MemberEntity;
import com.potato.ecommerce.domain.member.repository.MemberJpaRepository;
import com.potato.ecommerce.domain.product.entity.ProductEntity;
import com.potato.ecommerce.domain.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final ProductRepository productRepository;

    public void addCart(
        Long memberId,
        Long productId,
        Integer quantity
    ) {
        // member find
        MemberEntity memberEntity = memberJpaRepository.findById(memberId)
            .orElseThrow(() -> new EntityNotFoundException(
                "[ERROR] 유효하지 않은 사용자 입니다."));

        // product find
        ProductEntity productEntity = productRepository.findById(productId)
            .orElseThrow(() -> new EntityNotFoundException(
                "[ERROR] 유효하지 않은 상품 입니다."));

        // 어떤 멤버가 어떤 상품을 담았는지 확인
        CartEntity cartEntity =
            cartRepository.findByMember_IdAndProduct_Id(memberEntity.getId(),
                productEntity.getId());

        // 상품을 처음으로 담을 경우 장바구니를 생성
        if (cartEntity == null) {
            cartEntity = CartEntity.builder()
                .member(memberEntity)
                .product(productEntity)
                .quantity(quantity)
                .build();

            cartRepository.save(cartEntity);

            return;
        }
        // 상품이 이미 담겨있다면 수량을 업데이트
        cartEntity.addQuantity(quantity);
    }

    // 장바구니 상품 수량 변경
    public void updateCart(
        Long memberId,
        Long cartId,
        Integer quantity
    ) {
        // member 가 가진 cart find
        CartEntity cartEntity = cartRepository.findByMemberIdAndId(memberId, cartId)
            .orElseThrow(() -> new EntityNotFoundException(
                "[ERROR] 유효하지 않은 장바구니 입니다."));

        // 수량 update
        cartEntity.updateQuantity(quantity);
    }

    // 장바구니 상품 삭제
    public void deleteCart(
        Long memberId,
        Long cartId
    ) {
        // member 가 가진 cart find
        CartEntity cartEntity = cartRepository.findByMemberIdAndId(memberId, cartId)
            .orElseThrow(() -> new EntityNotFoundException(
                "[ERROR] 유효하지 않은 장바구니 입니다."));

        cartRepository.delete(cartEntity);
    }

    // 장바구니 목록 조회
    @Transactional(readOnly = true)
    public List<CartInfo> getCarts(Long memberId) {

        return cartRepository.findAllByMember_Id(memberId).stream().map(CartInfo::fromEntity)
            .toList();
    }
}
