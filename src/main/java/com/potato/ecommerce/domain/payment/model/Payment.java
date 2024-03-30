package com.potato.ecommerce.domain.payment.model;

import com.potato.ecommerce.domain.payment.vo.PaymentMethod;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    private PaymentMethod method;

    private LocalDateTime installmentPeriod;

}
