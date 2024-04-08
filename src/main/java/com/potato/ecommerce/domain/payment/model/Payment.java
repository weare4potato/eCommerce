package com.potato.ecommerce.domain.payment.model;

import com.potato.ecommerce.domain.payment.entity.PaymentEntity;
import com.potato.ecommerce.domain.payment.vo.PaymentType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Payment {

    private Long id;

    private Long totalPrice;

    private Long discountPrice;

    private PaymentType type;

    private LocalDateTime installmentPeriod;

    public static Payment fromEntity(PaymentEntity paymentEntity) {
        return Payment.builder()
            .totalPrice(paymentEntity.getTotalPrice())
            .discountPrice(paymentEntity.getDiscountPrice())
            .type(paymentEntity.getType())
            .installmentPeriod(paymentEntity.getInstallmentPeriod())
            .build();
    }

    public PaymentEntity toEntity() {
        return PaymentEntity.builder()
            .id(this.id)
            .totalPrice(this.totalPrice)
            .discountPrice(this.discountPrice)
            .type(this.type)
            .installmentPeriod(this.installmentPeriod)
            .build();
    }
}
