package com.potato.ecommerce.domain.store.repository;

import com.potato.ecommerce.domain.store.entity.StoreEntity;
import com.potato.ecommerce.domain.store.model.Store;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StoreRepositoryImpl implements StoreRepository {

    private final JpaStoreRepository jpaStoreRepository;


    @Override
    public boolean existsByEmail(String email) {
        return jpaStoreRepository.existsByEmail(email);
    }

    @Override
    public void save(Store store) {
        jpaStoreRepository.save(StoreEntity.fromModel(store));
    }

    @Override
    public void delete(Store store) {
        jpaStoreRepository.delete(StoreEntity.fromModel(store));
    }

    @Override
    public Store findByEmail(String email) {
        return jpaStoreRepository.findByEmail(email).orElseThrow(
            () -> new DataIntegrityViolationException("등록되지 않은 이메일입니다.")
        ).toModel();
    }

    @Override
    public Store findBySubject(String subject) {
        return jpaStoreRepository.findByBusinessNumber(subject).orElseThrow(
            () -> new NoSuchElementException("상점이 존재하지 않습니다.")
        ).toModel();
    }
}
