package com.potato.ecommerce.domain.store.repository;

import com.potato.ecommerce.domain.store.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<StoreEntity, Long> {

}
