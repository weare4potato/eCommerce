package com.potato.ecommerce.domain.store.repository;

import com.potato.ecommerce.domain.store.dto.UpdateStoreRequest;
import com.potato.ecommerce.domain.store.model.Store;

public interface StoreRepository {

    boolean existsByEmail(String email);

    void save(Store store);

    Store update(String subject, UpdateStoreRequest updateStoreRequest);

    void delete(Store store);

    Store findByEmail(String email);

    Store findBySubject(String businessNumber);
}
