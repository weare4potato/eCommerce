package com.potato.ecommerce.domain.payment.entity;

import com.potato.ecommerce.domain.payment.dto.TossWebhookDataDto;
import com.potato.ecommerce.domain.payment.dto.TossWebhookDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentWebhook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq", nullable = false)
    private Long seq;

    @Column
    @Setter
    private Long paymentSeq;

    @Column
    private String eventType;

    @Column
    private String paymentKey;

    @Column
    private String status;

    @Column
    private String orderId;

    public TossWebhookDto toDto() {
        return TossWebhookDto.builder()
            .paymentSeq(paymentSeq)
            .eventType(eventType)
            .data(TossWebhookDataDto
                .builder()
                .paymentKey(paymentKey)
                .status(status)
                .orderId(orderId)
                .build()
            )
            .build();
    }
}
