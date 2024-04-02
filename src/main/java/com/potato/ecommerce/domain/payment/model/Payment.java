package com.potato.ecommerce.domain.payment.model;

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

}
