package com.potato.ecommerce.domain.order.dto;

import com.potato.ecommerce.domain.member.model.Member;
import com.potato.ecommerce.domain.order.entity.OrderStatus;
import com.potato.ecommerce.domain.order.model.Order;
import com.potato.ecommerce.domain.payment.vo.PaymentType;
import com.potato.ecommerce.domain.receiver.model.Receiver;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfo {

    private Long id;
    private Member member;
    private Receiver receiver;
    private PaymentType paymentType;
    private OrderStatus status;
    private String orderNum;
    private LocalDateTime orderedAt;

    public static OrderInfo fromEntity(Order order) {
        return OrderInfo.builder()
            .id(order.getId())
            .member(order.getMember())
            .receiver(order.getReceiver())
            .paymentType(order.getPayment())
            .status(order.getStatus())
            .orderNum(order.getOrderNum())
            .orderedAt(order.getOrderedAt())
            .build();
    }
}
