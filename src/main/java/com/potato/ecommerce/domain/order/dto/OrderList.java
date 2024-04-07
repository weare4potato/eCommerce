package com.potato.ecommerce.domain.order.dto;

import com.potato.ecommerce.domain.order.entity.OrderEntity;
import com.potato.ecommerce.domain.order.entity.OrderStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderList {

    private Long id;
    private OrderStatus status;
    private String orderNum;
    private LocalDateTime orderedAt;
}
