package com.potato.ecommerce.global.exception.custom;

public class PaymentException extends RuntimeException {

    public PaymentException(String message) {
        super(message);
    }
}
