package com.potato.ecommerce.domain.order.dto;

import com.potato.ecommerce.domain.member.dto.ResponseMember;
import com.potato.ecommerce.domain.member.entity.MemberEntity;
import com.potato.ecommerce.domain.order.entity.OrderEntity;
import com.potato.ecommerce.domain.order.entity.OrderStatus;
import com.potato.ecommerce.domain.payment.vo.PaymentType;
import com.potato.ecommerce.domain.receiver.dto.ReceiverForm;
import com.potato.ecommerce.domain.receiver.entity.ReceiverEntity;
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
    private PaymentType paymentType;
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
            .paymentType(orderEntity.getPaymentType())
            .status(orderEntity.getStatus())
            .orderNum(orderEntity.getOrderNum())
            .orderedAt(orderEntity.getOrderedAt())
            .build();
    }
}
