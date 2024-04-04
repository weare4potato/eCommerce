package com.potato.ecommerce.domain.cart.repository;

import com.potato.ecommerce.domain.cart.entity.CartEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartEntity, Long> {

    CartEntity findByMember_IdAndProduct_Id(Long memberId, Long productId);

    List<CartEntity> findAllByMember_Id(Long memberId);

    Optional<CartEntity> findByMemberIdAndId(Long memberId, Long cartId);
}
