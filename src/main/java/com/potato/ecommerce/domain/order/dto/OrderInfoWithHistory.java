package com.potato.ecommerce.domain.order.dto;

import com.potato.ecommerce.domain.member.dto.ResponseMember;
import com.potato.ecommerce.domain.order.entity.OrderEntity;
import com.potato.ecommerce.domain.order.entity.OrderStatus;
import com.potato.ecommerce.domain.payment.vo.PaymentType;
import com.potato.ecommerce.domain.receiver.dto.ReceiverForm;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class OrderInfoWithHistory {

    private Long id;
    private ResponseMember member;
    private ReceiverForm receiver;
    private PaymentType paymentType;
    private OrderStatus status;
    private String orderNum;
    private List<HistoryInfo> historyInfos;
    private LocalDateTime orderedAt;
    private Long totalAmount;

    public static OrderInfoWithHistory fromEntity(
        OrderEntity order,
        List<HistoryInfo> historyInfos,
        ResponseMember member,
        ReceiverForm receiver
    ) {
        log.info(order.getMember().getEmail());
        return OrderInfoWithHistory.builder()
            .id(order.getId())
            .member(member)
            .receiver(receiver)
            .paymentType(order.getPaymentType())
            .status(order.getStatus())
            .orderNum(order.getOrderNum())
            .historyInfos(historyInfos)
            .orderedAt(order.getOrderedAt())
            .totalAmount(order.getTotalAmount())
            .build();
    }
}
