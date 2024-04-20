package com.potato.ecommerce.domain.payment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TossPaymentRequest {

    private Integer amount;
    private String orderId;
    private String paymentKey;

}
