package com.potato.ecommerce.domain.revenue.service;

import com.potato.ecommerce.domain.revenue.dto.RevenueResponse;
import com.potato.ecommerce.domain.revenue.entity.RevenueEntity;
import com.potato.ecommerce.domain.revenue.repository.RevenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RevenueService {

    private final RevenueRepository revenueRepository;
    private Long number = 1L;

    public RevenueResponse getBusinessNumber() {
        RevenueEntity revenueEntity = new RevenueEntity(number++);

        RevenueEntity revenue = revenueRepository.save(revenueEntity);

        return new RevenueResponse(revenue.getNumber());
    }
}
