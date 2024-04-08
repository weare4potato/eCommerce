package com.potato.ecommerce.domain.order.repository.history;

import com.potato.ecommerce.domain.order.entity.HistoryEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryJpaRepository extends JpaRepository<HistoryEntity, Long> {

    List<HistoryEntity> findAllByOrderId(Long orderId);
}
