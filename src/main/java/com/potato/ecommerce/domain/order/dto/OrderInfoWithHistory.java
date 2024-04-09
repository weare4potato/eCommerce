package com.potato.ecommerce.domain.order.dto;

import com.potato.ecommerce.domain.member.entity.MemberEntity;
import com.potato.ecommerce.domain.order.entity.OrderEntity;
import com.potato.ecommerce.domain.order.entity.OrderStatus;
import com.potato.ecommerce.domain.payment.vo.PaymentType;
import com.potato.ecommerce.domain.receiver.entity.ReceiverEntity;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfoWithHistory {

    private Long id;
    private MemberEntity member;
    private ReceiverEntity receiver;
    private PaymentType paymentType;
    private OrderStatus status;
    private String orderNum;
    private List<HistoryInfo> historyInfos;
    private LocalDateTime orderedAt;

    public static OrderInfoWithHistory fromEntity(
        OrderEntity order,
        List<HistoryInfo> historyInfos
    ) {
        return OrderInfoWithHistory.builder()
            .id(order.getId())
            .member(order.getMember())
            .receiver(order.getReceiver())
            .paymentType(order.getPaymentType())
            .status(order.getStatus())
            .orderNum(order.getOrderNum())
            .historyInfos(historyInfos)
            .orderedAt(order.getOrderedAt())
            .build();
    }
}
