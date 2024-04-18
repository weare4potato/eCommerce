package com.potato.ecommerce.domain.payment.dto;

import lombok.Data;

@Data
public class CancelPaymentReq {

    private String paymentKey;
    private String cancelReason;
    private REFUND_BANK_TYPE bank;
    private String accountNumber;
    private String holderName;

    public RefundReceiveAccountDto getRefundAccountDto() {
        return RefundReceiveAccountDto.builder()
            .bank(bank)
            .accountNumber(accountNumber)
            .holderName(holderName)
            .build();
    }
}
