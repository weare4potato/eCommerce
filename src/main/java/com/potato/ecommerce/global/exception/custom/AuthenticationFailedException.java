package com.potato.ecommerce.global.exception.custom;

public class AuthenticationFailedException extends RuntimeException {

    public AuthenticationFailedException() {
        super("이메일 인증을 하지 않은 유저입니다.");
    }
}
