package com.potato.ecommerce.domain.order.dto;

import com.potato.ecommerce.domain.member.dto.ResponseMember;
import com.potato.ecommerce.domain.order.entity.OrderEntity;
import com.potato.ecommerce.domain.order.entity.OrderStatus;
import com.potato.ecommerce.domain.payment.dto.PayType;
import com.potato.ecommerce.domain.receiver.dto.ReceiverForm;
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
    private ResponseMember member;
    private ReceiverForm receiver;
    private PayType payType;
    private OrderStatus status;
    private String orderNum;
    private LocalDateTime orderedAt;

    public static OrderInfo fromEntity(
        OrderEntity orderEntity,
        ResponseMember member,
        ReceiverForm receiver
    ) {
        return OrderInfo.builder()
            .id(orderEntity.getId())
            .member(member)
            .receiver(receiver)
            .payType(orderEntity.getPayType())
            .status(orderEntity.getStatus())
            .orderNum(orderEntity.getOrderNum())
            .orderedAt(orderEntity.getOrderedAt())
            .build();
    }
}
