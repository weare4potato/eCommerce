package com.potato.ecommerce.domain.product.dto;

import com.potato.ecommerce.domain.store.dto.StoreResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ProductResponse {

    private final Long id;
    private final String name;
    private final String description;
    private final Long price;
    private final Integer stock;
    private final StoreResponse store;
    private final Long productCategoryId;

}

