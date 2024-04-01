package com.potato.ecommerce.global.exception;

public enum ExceptionMessage {
    MEMBER_NOT_FOUND("해당 유저를 찾지 못했습니다."),
    PASSWORD_NOT_MATCH("비밀번호가 일치하지 않습니다.");

    private final String message;

    ExceptionMessage(String message){
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
