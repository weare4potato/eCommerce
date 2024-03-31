package com.potato.ecommerce.domain.revenue.repository;


import com.potato.ecommerce.domain.revenue.entity.RevenueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RevenueRepository extends JpaRepository<RevenueEntity, Long> {

}
