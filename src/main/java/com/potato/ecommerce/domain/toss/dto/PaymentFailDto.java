package com.potato.ecommerce.domain.toss.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PaymentFailDto {

    String errorCode;
    String errorMessage;
    String orderId;
}
