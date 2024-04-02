package com.potato.ecommerce.domain.order.repository;

import com.potato.ecommerce.domain.order.entity.HistoryEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<HistoryEntity, Long> {
    List<HistoryEntity> findAllByOrderId(Long orderId);
}
