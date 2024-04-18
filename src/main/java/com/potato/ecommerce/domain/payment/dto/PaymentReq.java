package com.potato.ecommerce.domain.payment.dto;

import com.potato.ecommerce.domain.payment.config.DateConfig;
import com.potato.ecommerce.domain.payment.entity.Payment;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentReq {

    private PAY_TYPE payType;
    private Long amount;
    private ORDER_NAME_TYPE orderName;
    private String customerEmail;
    private String customerName;

    public Payment toEntity() {
        return Payment.builder()
            .orderId(UUID.randomUUID().toString())
            .payType(payType)
            .amount(amount)
            .orderName(orderName)
            .customerEmail(customerEmail)
            .customerName(customerName)
            .cancelYn("N")
            .paySuccessYn("N")
            .createDate(new DateConfig().getNowDate())
            .build();
    }
}
