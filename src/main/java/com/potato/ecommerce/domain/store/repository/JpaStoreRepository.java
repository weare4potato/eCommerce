package com.potato.ecommerce.domain.store.repository;

import com.potato.ecommerce.domain.store.entity.StoreEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaStoreRepository extends JpaRepository<StoreEntity, Long> {

    Optional<StoreEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<StoreEntity> findByBusinessNumber(String subject);
}
