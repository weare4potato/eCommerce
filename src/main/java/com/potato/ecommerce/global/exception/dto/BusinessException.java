package com.potato.ecommerce.global.exception.dto;

import com.potato.ecommerce.domain.payment.util.ExMessage;

public class BusinessException extends RuntimeException {

    public BusinessException(ExMessage exMessage) {
        super(exMessage.getMessage());
    }

    public BusinessException(String message) {
        super(message);
    }
}
