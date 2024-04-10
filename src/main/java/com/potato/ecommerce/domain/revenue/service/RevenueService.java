package com.potato.ecommerce.domain.revenue.service;

import com.potato.ecommerce.domain.revenue.dto.RevenueResponse;
import com.potato.ecommerce.domain.revenue.entity.RevenueEntity;
import com.potato.ecommerce.domain.revenue.repository.RevenueRepository;
import java.util.concurrent.atomic.AtomicLong;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RevenueService {

    private final RevenueRepository revenueRepository;
    private final AtomicLong seq = new AtomicLong(1);

    @Transactional
    public RevenueResponse getBusinessNumber() {
        RevenueEntity revenueEntity = new RevenueEntity(seq);

        RevenueEntity revenue = revenueRepository.save(revenueEntity);

        return new RevenueResponse(revenue.getNumber());
    }
}
