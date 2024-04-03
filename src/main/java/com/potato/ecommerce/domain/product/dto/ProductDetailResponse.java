package com.potato.ecommerce.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductDetailResponse {

    private String oneDepth;
    private String twoDepth;
    private String threeDepth;
    private String productName;
    private String description;
    private String price;

}
