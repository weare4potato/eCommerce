package com.potato.ecommerce.domain.store.service;

import com.potato.ecommerce.domain.product.dto.ProductListResponse;
import com.potato.ecommerce.domain.product.repository.ProductQueryRepository;
import com.potato.ecommerce.domain.store.model.Store;
import com.potato.ecommerce.domain.store.repository.StoreRepository;
import com.potato.ecommerce.global.util.RestPage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreManagementService {

    private final StoreRepository storeRepository;
    private final ProductQueryRepository productQueryRepository;

    public RestPage<ProductListResponse> getProducts(String subject, int page, int size) {
        return productQueryRepository.getProducts(subject, page, size);
    }

    private Store findBySubject(String subject) {
        return storeRepository.findBySubject(subject);
    }
}
