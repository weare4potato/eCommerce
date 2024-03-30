package com.potato.ecommerce.domain.product.repository;

import com.potato.ecommerce.domain.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {


}
