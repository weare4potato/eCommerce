package com.potato.ecommerce.domain.store.repository;

import static com.potato.ecommerce.domain.store.model.Store.fromEntity;

import com.potato.ecommerce.domain.store.model.Store;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityNotFoundException;
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
        jpaStoreRepository.save(store.toEntity());
    }

    @Override
    public void delete(Store store) {
        jpaStoreRepository.delete(store.toEntity());
    }

    @Override
    public Store findByEmail(String email) {
        return fromEntity(jpaStoreRepository.findByEmail(email).orElseThrow(
            () -> new EntityNotFoundException("등록되지 않은 이메일입니다.")
        ));
    }

    @Override
    public Store findBySubject(String subject) {
        return fromEntity(jpaStoreRepository.findByBusinessNumber(subject).orElseThrow(
            () -> new EntityNotFoundException("상점이 존재하지 않습니다.")
        ));
    }
}
