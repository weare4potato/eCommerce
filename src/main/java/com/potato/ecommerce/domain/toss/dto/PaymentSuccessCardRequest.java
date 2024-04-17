package com.potato.ecommerce.domain.toss.dto;

import lombok.Data;

@Data
public class PaymentSuccessCardRequest {

    String company;
    String number;
    String installmentPlanMonths;
    String isInterestFree;
    String approveNo;
    String useCardPoint;
    String cardType;
    String ownerType;
    String acquireStatus;
    String receiptUrl;
}
