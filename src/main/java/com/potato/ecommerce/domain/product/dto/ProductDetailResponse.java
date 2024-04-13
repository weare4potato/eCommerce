package com.potato.ecommerce.domain.product.dto;

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
    private String price;
    private Long storeId;

}
