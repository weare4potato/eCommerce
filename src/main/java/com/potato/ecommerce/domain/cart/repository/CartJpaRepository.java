package com.potato.ecommerce.domain.cart.repository;

import com.potato.ecommerce.domain.cart.entity.CartEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartJpaRepository extends JpaRepository<CartEntity, Long> {

    Optional<CartEntity> findByMember_IdAndProduct_Id(Long memberId, Long productId);

    List<CartEntity> findAllByMember_Email(String email);

    Optional<CartEntity> findByMemberEmailAndId(String email, Long cartId);

    Optional<CartEntity> findByMemberIdAndId(Long memberId, Long cartId);
}
