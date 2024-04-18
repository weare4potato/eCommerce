package com.potato.ecommerce.domain.payment.dto;

import com.potato.ecommerce.domain.payment.entity.PaymentWebhook;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TossWebhookDto {

    private Long paymentSeq;
    private String eventType;
    private TossWebhookDataDto data;

    public PaymentWebhook toEntity() {
        return PaymentWebhook.builder()
            .eventType(eventType)
            .paymentKey(data.getPaymentKey())
            .status(data.getStatus())
            .orderId(data.getOrderId())
            .build();
    }
}
