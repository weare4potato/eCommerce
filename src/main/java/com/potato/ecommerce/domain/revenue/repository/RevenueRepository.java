package com.potato.ecommerce.domain.revenue.repository;

import com.potato.ecommerce.domain.revenue.model.Revenue;

public interface RevenueRepository {

    Revenue save(Revenue revenue);

    Revenue findByNumber(String businessNumber);
}
