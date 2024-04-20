package com.potato.ecommerce.domain.order.repository.order;

import com.potato.ecommerce.domain.order.entity.OrderEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {

    Optional<OrderEntity> findByOrderNum(String orderName);
}
