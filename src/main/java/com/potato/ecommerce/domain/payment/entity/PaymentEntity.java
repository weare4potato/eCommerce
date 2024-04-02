package com.potato.ecommerce.domain.payment.entity;

import com.potato.ecommerce.domain.payment.model.Payment;
import com.potato.ecommerce.domain.payment.vo.PaymentType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "payments")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @Column(nullable = false)
    private Long totalPrice;

    @Column(nullable = false)
    private Long discountPrice;

    @Enumerated(EnumType.STRING)
    private PaymentType type;

    @Column(nullable = false)
    private LocalDateTime installmentPeriod;

    @Builder
    private PaymentEntity(Long totalPrice, Long discountPrice, PaymentType type,
        LocalDateTime installmentPeriod) {
        this.totalPrice = totalPrice;
        this.discountPrice = discountPrice;
        this.type = type;
        this.installmentPeriod = installmentPeriod;
    }

    public static PaymentEntity fromModel(Payment payment) {
        return PaymentEntity.builder()
            .totalPrice(payment.getTotalPrice())
            .discountPrice(payment.getDiscountPrice())
            .type(payment.getType())
            .installmentPeriod(payment.getInstallmentPeriod())
            .build();
    }

    public Payment toModel() {
        return Payment.builder()
            .id(this.id)
            .totalPrice(this.totalPrice)
            .discountPrice(this.discountPrice)
            .type(this.type)
            .installmentPeriod(this.installmentPeriod)
            .build();
    }
}
