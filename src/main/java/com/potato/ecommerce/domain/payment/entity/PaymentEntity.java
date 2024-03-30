package com.potato.ecommerce.domain.payment.entity;

import com.potato.ecommerce.domain.payment.model.Payment;
import com.potato.ecommerce.domain.payment.vo.PaymentMethod;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "payments")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @Column(nullable = false)
    private Long totalPrice;

    @Column
    private Long discountPrice;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;


    private LocalDateTime installmentPeriod;

    public static PaymentEntity fromModel(Payment payment){
        return PaymentEntity.builder()
            .totalPrice(payment.getTotalPrice())
            .discountPrice(payment.getDiscountPrice())
            .method(payment.getMethod())
            .installmentPeriod(payment.getInstallmentPeriod())
            .build();
    }

    public Payment toModel(){
        return Payment.builder()
            .id(this.id)
            .totalPrice(this.totalPrice)
            .discountPrice(this.discountPrice)
            .method(this.method)
            .installmentPeriod(this.installmentPeriod)
            .build();
    }
}
