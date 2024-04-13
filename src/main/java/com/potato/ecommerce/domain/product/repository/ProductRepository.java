package com.potato.ecommerce.domain.product.repository;

import com.potato.ecommerce.domain.product.entity.ProductEntity;
import com.potato.ecommerce.domain.store.entity.StoreEntity;
import jakarta.mail.Store;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByStoreId(Long storeId);

    List<ProductEntity> findByProductCategory_Id(Long productCategoryId);

    Page<ProductEntity> findAll(Pageable pageable);

    Page<ProductEntity> findByStoreId(Long storeId, Pageable pageable);
    Page<ProductEntity> findByProductCategory_Id(Long productCategoryId, Pageable pageable);

    @Query("SELECT s.store FROM ProductEntity s JOIN s.store p WHERE p.id = :productId")
    Optional<StoreEntity> findStoreEntityByProductEntity(Long productId);
}
