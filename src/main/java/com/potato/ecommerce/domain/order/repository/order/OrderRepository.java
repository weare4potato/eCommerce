package com.potato.ecommerce.domain.order.repository.order;

import com.potato.ecommerce.domain.order.model.Order;

public interface OrderRepository {

    Order save(Order order);
    Order update(Order order);
    Order findById(Long orderId);
}
