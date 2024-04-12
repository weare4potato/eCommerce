package com.potato.ecommerce.domain.category.repository;

import com.potato.ecommerce.domain.category.entity.ProductCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity, Long> {

}
