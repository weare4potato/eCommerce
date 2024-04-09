package com.potato.ecommerce.domain.revenue.repository;

import static com.potato.ecommerce.global.exception.ExceptionMessage.BUSINESS_NUMBER_NOT_FOUNT;

import com.potato.ecommerce.domain.revenue.entity.RevenueEntity;
import com.potato.ecommerce.domain.revenue.model.Revenue;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RevenueRepositoryImpl implements RevenueRepository {

    private final JpaRevenueRepository jpaRevenueRepository;

    @Override
    public Revenue save(Revenue revenue) {
        return jpaRevenueRepository.save(RevenueEntity.fromModel(revenue)).toModel();
    }

    @Override
    public Revenue findByNumber(String businessNumber) {
        return jpaRevenueRepository.findByNumber(businessNumber).orElseThrow(
            () -> new NoSuchElementException(BUSINESS_NUMBER_NOT_FOUNT.toString())
        ).toModel();
    }
}
