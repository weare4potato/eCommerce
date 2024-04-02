package com.potato.ecommerce.domain.store.repository;

import com.potato.ecommerce.domain.store.entity.StoreEntity;
import com.potato.ecommerce.domain.store.model.Store;

public interface StoreRepository {
    boolean existsByEmail(String email);
    void save(Store store);
    void delete(Store store);
    Store findByEmail(String email);
    Store findBySubject(String businessNumber);
}
