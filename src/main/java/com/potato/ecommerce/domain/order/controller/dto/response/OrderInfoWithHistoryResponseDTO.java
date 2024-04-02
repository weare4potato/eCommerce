package com.potato.ecommerce.domain.order.controller.dto.response;

import com.potato.ecommerce.domain.member.model.Member;
import com.potato.ecommerce.domain.order.dto.HistoryInfo;
import com.potato.ecommerce.domain.order.dto.OrderInfoWithHistory;
import com.potato.ecommerce.domain.order.entity.OrderStatus;
import com.potato.ecommerce.domain.payment.vo.PaymentType;
import com.potato.ecommerce.domain.receiver.model.Receiver;
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
public class OrderInfoWithHistoryResponseDTO {
    private Long id;
    private Member member;
    private Receiver receiver;
    private PaymentType paymentType;
    private OrderStatus status;
    private String orderNum;
    private List<HistoryInfo> historyInfos;
    private LocalDateTime orderedAt;
    private Long totalAmount;

    public static OrderInfoWithHistoryResponseDTO from(OrderInfoWithHistory info){
        Long totalAmount = 0L;

        for (HistoryInfo historyInfo : info.getHistoryInfos()) {
            Integer price = historyInfo.getProduct().getPrice();
            Integer quantity = historyInfo.getQuantity();
            totalAmount += price * quantity;
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
