package com.potato.ecommerce.domain.revenue.service;

import com.potato.ecommerce.domain.revenue.dto.RevenueResponse;
import com.potato.ecommerce.domain.revenue.entity.RevenueEntity;
import com.potato.ecommerce.domain.revenue.repository.RevenueRepository;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RevenueService {

    private final RevenueRepository revenueRepository;
    private AtomicLong seq = new AtomicLong(1);

    public RevenueResponse getBusinessNumber() {
        RevenueEntity revenueEntity = new RevenueEntity(seq);

        RevenueEntity revenue = revenueRepository.save(revenueEntity);

        return new RevenueResponse(revenue.getNumber());
    }
}
