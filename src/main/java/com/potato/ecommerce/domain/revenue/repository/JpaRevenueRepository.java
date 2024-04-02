package com.potato.ecommerce.domain.revenue.repository;


import com.potato.ecommerce.domain.revenue.entity.RevenueEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRevenueRepository extends JpaRepository<RevenueEntity, Long> {

    Optional<RevenueEntity> findByNumber(String businessNumber);
}
