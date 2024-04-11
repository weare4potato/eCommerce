package com.potato.ecommerce.domain.order.controller.dto.response;

import com.potato.ecommerce.domain.member.dto.ResponseMember;
import com.potato.ecommerce.domain.member.entity.MemberEntity;
import com.potato.ecommerce.domain.order.dto.HistoryInfo;
import com.potato.ecommerce.domain.order.dto.OrderInfoWithHistory;
import com.potato.ecommerce.domain.order.entity.OrderStatus;
import com.potato.ecommerce.domain.payment.vo.PaymentType;
import com.potato.ecommerce.domain.receiver.dto.ReceiverForm;
import com.potato.ecommerce.domain.receiver.entity.ReceiverEntity;
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
public class OrderInfoWithHistoryResponseDTO {

    private Long id;
    private ResponseMember member;
    private ReceiverForm receiver;
    private PaymentType paymentType;
    private OrderStatus status;
    private String orderNum;
    private List<HistoryInfo> historyInfos;
    private LocalDateTime orderedAt;
    private Long totalAmount;

    public static OrderInfoWithHistoryResponseDTO from(OrderInfoWithHistory info) {
        Long totalAmount = 0L;

        for (HistoryInfo historyInfo : info.getHistoryInfos()) {
            totalAmount += historyInfo.getOrderPrice();
        }

        return OrderInfoWithHistoryResponseDTO.builder()
            .id(info.getId())
            .member(info.getMember())
            .receiver(info.getReceiver())
            .paymentType(info.getPaymentType())
            .status(info.getStatus())
            .orderNum(info.getOrderNum())
            .historyInfos(info.getHistoryInfos())
            .orderedAt(info.getOrderedAt())
            .totalAmount(totalAmount)
            .build();
    }
}
