package com.potato.ecommerce.domain.cartProduct.repository;

import com.potato.ecommerce.domain.cartProduct.entity.CartProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartProductRepository extends JpaRepository<CartProductEntity, Long> {

}
