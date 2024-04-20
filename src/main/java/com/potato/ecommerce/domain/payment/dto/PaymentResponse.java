package com.potato.ecommerce.domain.payment.dto;

import com.potato.ecommerce.domain.payment.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {

    private Long id;
    private String itemName;
    private Integer price;
    private boolean isPaid;

    public PaymentResponse(Payment payment) {
        this.id = payment.getId();
        this.price = payment.getPrice();
    }
}
