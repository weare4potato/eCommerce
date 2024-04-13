package com.potato.ecommerce.domain.product.dto;

import com.potato.ecommerce.domain.store.dto.StoreResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductDetailResponse {

    private String oneDepthDescription;
    private String twoDepthDescription;
    private String threeDepthDescription;
    private String productName;
    private String description;
    private Long price;
    private StoreResponse store;

}
