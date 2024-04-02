package com.potato.ecommerce.domain.store.service;

import com.potato.ecommerce.domain.store.repository.JpaStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreManagementService {

    private final JpaStoreRepository jpaStoreRepository;


}
