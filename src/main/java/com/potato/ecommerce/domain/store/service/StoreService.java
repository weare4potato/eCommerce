package com.potato.ecommerce.domain.store.service;

import com.potato.ecommerce.domain.revenue.repository.RevenueRepository;
import com.potato.ecommerce.domain.store.dto.StoreRequest;
import com.potato.ecommerce.domain.store.entity.StoreEntity;
import com.potato.ecommerce.domain.store.model.Store;
import com.potato.ecommerce.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final RevenueRepository revenueRepository;

    public void signup(StoreRequest storeRequest) {

        storeRepository.findByEmail(storeRequest.getEmail()).ifPresent(
            s -> {
                throw new DataIntegrityViolationException("이미 존재하는 이메일입니다.");
            });

        revenueRepository.findByNumber(storeRequest.getBusinessNumber()).ifPresent(
            s -> {
                throw new DataIntegrityViolationException("이미 존재하는 사업자 번호입니다.");
            });

        Store store = Store.builder()
            .email(storeRequest.getEmail())
            .password(storeRequest.getPassword())
            .name(storeRequest.getName())
            .phone(storeRequest.getPhone())
            .description(storeRequest.getDescription())
            .licenseNumber(storeRequest.getBusinessNumber())
            .build();

        storeRepository.save(StoreEntity.fromModel(store));
    }
}
