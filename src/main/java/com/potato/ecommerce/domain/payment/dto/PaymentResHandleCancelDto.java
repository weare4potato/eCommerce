package com.potato.ecommerce.domain.payment.dto;

import lombok.Data;

@Data
public class PaymentResHandleCancelDto {

    Long cancelAmount;           // 결제를 취소한 금액입니다.
    String cancelReason;            // 결제를 취소한 이유입니다.
    Integer taxFreeAmount;          // 면세 처리된 금액입니다.
    Integer taxAmount;              // 과세 처리된 금액입니다.
    Integer refundableAmount;       // 결제 취소 후 환불 가능한 잔액입니다.
    String canceledAt;              // 결제 취소가 일어난 날짜와 시간 정보입니다.
}
