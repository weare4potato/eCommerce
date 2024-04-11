package com.potato.ecommerce.domain.order.controller.dto.response;

import com.potato.ecommerce.domain.member.dto.ResponseMember;
import com.potato.ecommerce.domain.order.dto.OrderInfo;
import com.potato.ecommerce.domain.order.entity.OrderStatus;
import com.potato.ecommerce.domain.payment.vo.PaymentType;
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
public class OrderInfoResponseDTO {

    private Long id;
    private ResponseMember member;
    private ReceiverForm receiver;
    private PaymentType paymentType;
    private OrderStatus status;
    private String orderNum;
    private LocalDateTime orderedAt;

    public static OrderInfoResponseDTO from(OrderInfo info) {
        return OrderInfoResponseDTO.builder()
            .id(info.getId())
            .member(info.getMember())
            .receiver(info.getReceiver())
            .paymentType(info.getPaymentType())
            .status(info.getStatus())
            .orderNum(info.getOrderNum())
            .orderedAt(info.getOrderedAt())
            .build();
    }
}
