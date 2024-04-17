package com.potato.ecommerce.domain.toss.dto;

import com.potato.ecommerce.domain.toss.entity.PaymentType;
import com.potato.ecommerce.domain.toss.entity.PaymentEntity;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    @NotNull
    private PaymentType paymentType;

    @NotNull
    private Long amount;

    @NotNull
    private String orderName;

    @NotNull
    private String customerEmail;

    @NotNull
    private String customerName;

    public PaymentEntity toEntity() {
        return PaymentEntity.builder()
            .orderId(UUID.randomUUID().toString())
            .paymentType(paymentType)
            .amount(amount)
            .orderName(orderName)
            .customerEmail(customerEmail)
            .customerName(customerName)
            .paySuccessYN(true)
            .build();
    }
}
