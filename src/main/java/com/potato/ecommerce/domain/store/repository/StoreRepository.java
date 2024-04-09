package com.potato.ecommerce.domain.store.repository;

import com.potato.ecommerce.domain.store.dto.UpdateStoreRequest;
import com.potato.ecommerce.domain.store.entity.StoreEntity;

public interface StoreRepository {

    boolean existsByEmail(String email);

    void save(StoreEntity storeEntity);

    StoreEntity update(String subject, UpdateStoreRequest updateStoreRequest);

    void delete(StoreEntity storeEntity);

    StoreEntity findByEmail(String email);

    StoreEntity findBySubject(String businessNumber);
}
