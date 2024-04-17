package com.potato.ecommerce.domain.toss.dto;

import com.potato.ecommerce.domain.toss.dto.PaymentSuccessCardRequest;
import lombok.Data;

@Data
public class PaymentSuccessResponse {

    String mid;
    String version;
    String paymentKey;
    String orderId;
    String orderName;
    String currency;
    String method;
    String totalAmount;
    String balanceAmount;
    String suppliedAmount;
    String vat;
    String status;
    String requestAt;
    String approvedAt;
    String useEscrow;
    String cultureExpense;
    PaymentSuccessCardRequest card;
    String type;
}
