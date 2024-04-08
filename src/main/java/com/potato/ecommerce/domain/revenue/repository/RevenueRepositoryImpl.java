package com.potato.ecommerce.domain.revenue.repository;

import static com.potato.ecommerce.domain.revenue.model.Revenue.fromEntity;
import static com.potato.ecommerce.global.exception.ExceptionMessage.BUSINESS_NUMBER_NOT_FOUNT;

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
        return fromEntity(jpaRevenueRepository.save(revenue.toEntity()));
    }

    @Override
    public Revenue findByNumber(String businessNumber) {
        return fromEntity(jpaRevenueRepository.findByNumber(businessNumber).orElseThrow(
            () -> new NoSuchElementException(BUSINESS_NUMBER_NOT_FOUNT.toString())
        ));
    }
}
