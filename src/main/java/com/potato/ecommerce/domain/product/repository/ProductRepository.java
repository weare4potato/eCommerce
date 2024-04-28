package com.potato.ecommerce.domain.product.repository;

import com.potato.ecommerce.domain.product.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Page<ProductEntity> findAll(Pageable pageable);

    Page<ProductEntity> findByStoreId(Long storeId, Pageable pageable);

    Page<ProductEntity> findByProductCategory_Id(Long productCategoryId, Pageable pageable);

    Page<ProductEntity> findAllByNameContaining(String name, Pageable pageable);

}
