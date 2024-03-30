package com.potato.ecommerce.domain.cart.repository;

import com.potato.ecommerce.domain.cart.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartEntity, Long> {

}
