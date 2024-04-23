package com.potato.ecommerce.domain.cart.service;

import static com.potato.ecommerce.global.config.redis.CacheConfig.CACHE_180_SECOND;

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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartJpaRepository cartJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final ProductRepository productRepository;

    @CacheEvict(cacheNames = CACHE_180_SECOND, key = "'cart:' + #p0", beforeInvocation = false)
    public CartInfo addCart(
        String email,
        Long productId,
        Integer quantity
    ) {
        MemberEntity memberEntity = memberJpaRepository.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException(
                ExceptionMessage.MEMBER_NOT_FOUND.toString()));

        ProductEntity productEntity = productRepository.findById(productId)
            .orElseThrow(() -> new EntityNotFoundException(
                ExceptionMessage.PRODUCT_NOT_FOUND.toString()));

        CartEntity cartEntity =
            cartJpaRepository.
                findByMemberEmailAndProductId(memberEntity.getEmail(), productEntity.getId())
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

    @CacheEvict(cacheNames = CACHE_180_SECOND, key = "'cart:' + #p0", beforeInvocation = false)
    public CartInfo updateCart(
        String email,
        Long cartId,
        Integer quantity
    ) {
        CartEntity cartEntity = cartJpaRepository.findByMemberEmailAndId(email, cartId)
            .orElseThrow(() -> new EntityNotFoundException(
                ExceptionMessage.CART_NOT_FOUND.toString()));

        cartEntity.updateQuantity(quantity);

        return CartInfo.fromEntity(cartEntity);
    }

    @CacheEvict(cacheNames = CACHE_180_SECOND, key = "'cart:' + #p0", beforeInvocation = false)
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
    @Cacheable(cacheNames = CACHE_180_SECOND, key = "'cart:' + #p0")
    public List<CartInfo> getCarts(String email) {

        return cartJpaRepository.findAllByMember_Email(email).stream().map(CartInfo::fromEntity)
            .toList();
    }
}
