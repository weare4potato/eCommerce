package com.potato.ecommerce.domain.order.repository.order;

import static com.potato.ecommerce.domain.order.model.Order.fromEntity;

import com.potato.ecommerce.domain.order.model.Order;
import com.potato.ecommerce.global.exception.ExceptionMessage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    @Override
    public Order save(Order order) {

        return fromEntity(orderJpaRepository.save(order.toEntity()));
    }

    @Override
    public Order update(Order order) {

        return fromEntity(orderJpaRepository.saveAndFlush(order.toEntity()));
    }

    @Override
    public Order findById(Long orderId) {
        return fromEntity(orderJpaRepository.findById(orderId)
            .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.ORDER_NOT_FOUND.toString())));
    }
}
