package com.potato.ecommerce.domain.revenue.service;

import com.potato.ecommerce.domain.revenue.dto.RevenueResponse;
import com.potato.ecommerce.domain.revenue.entity.RevenueEntity;
import com.potato.ecommerce.domain.revenue.model.Revenue;
import com.potato.ecommerce.domain.revenue.repository.RevenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RevenueService {

    private final RevenueRepository revenueRepository;
    private Long number = 1L;

    public RevenueResponse getBusinessNumber() {
        Revenue revenue = new Revenue(number++);

        Revenue model = revenueRepository.save(
            RevenueEntity.fromModel(revenue)).toModel();

        return new RevenueResponse(model.getNumber());
    }
}
