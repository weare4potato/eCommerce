package com.potato.ecommerce.global.exception.dto;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
