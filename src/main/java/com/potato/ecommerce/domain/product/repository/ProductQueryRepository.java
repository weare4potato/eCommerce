package com.potato.ecommerce.domain.product.repository;

import com.potato.ecommerce.domain.product.dto.ProductListResponse;
import com.potato.ecommerce.global.util.RestPage;

public interface ProductQueryRepository {

    RestPage<ProductListResponse> getProducts(String subject, int page, int size);
}
