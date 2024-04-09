package com.potato.ecommerce.domain.store.repository;

import com.potato.ecommerce.domain.store.dto.UpdateStoreRequest;
import com.potato.ecommerce.domain.store.entity.StoreEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
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
    public void save(StoreEntity storeEntity) {
        jpaStoreRepository.save(storeEntity);
    }

    @Override
    public void delete(StoreEntity storeEntity) {
        jpaStoreRepository.delete(storeEntity);
    }

    @Override
    public StoreEntity findByEmail(String email) {
        return jpaStoreRepository.findByEmail(email).orElseThrow(
            () -> new EntityNotFoundException("등록되지 않은 이메일입니다.")
        );
    }

    @Override
    public StoreEntity findBySubject(String subject) {
        return jpaStoreRepository.findByBusinessNumber(subject).orElseThrow(
            () -> new EntityNotFoundException("상점이 존재하지 않습니다.")
        );
    }

    @Override
    public StoreEntity update(String subject, UpdateStoreRequest updateStoreRequest) {
        StoreEntity storeEntity = jpaStoreRepository.findByBusinessNumber(subject).orElseThrow(
            () -> new EntityNotFoundException("상점이 존재하지 않습니다.")
        );

        storeEntity.update(updateStoreRequest.getName(), updateStoreRequest.getDescription(),
            updateStoreRequest.getPhone());

        return storeEntity;
    }
}
