package com.potato.ecommerce.domain.order.repository;

import com.potato.ecommerce.domain.order.dto.OrderList;
import com.potato.ecommerce.global.util.RestPage;

public interface OrderQueryRepository {

    RestPage<OrderList> getOrders(String subject, int page, int size);
}
