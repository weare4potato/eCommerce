package com.potato.ecommerce.global.exception;

public enum ExceptionMessage {
    MEMBER_NOT_FOUND("해당 유저를 찾지 못했습니다."),
    PASSWORD_NOT_MATCH("비밀번호가 일치하지 않습니다."),
    CHANGE_PASSWORD_CHECK("현재 쓰는 비밀번호와 같습니다."),
    RECEIVER_NOT_FOUND("해당 주소지를 찾지 못했습니다."),
    DUPLICATE_EMAIL("이미 존재하는 email 입니다."),
    EMAIL_NOT_MATCH("이메일이 일치하지 않습니다."),
    BUSINESS_NUMBER_NOT_MATCH("사업자 등록 번호가 일치하지 않습니다."),
    DUPLICATE_BUSINESS_NUMBER("이미 사용된 사업자 번호입니다."),
    BUSINESS_NUMBER_NOT_FOUNT("발급된 사업자 번호가 아닙니다."),
    RECEIVER_NOT_MATCH("주소지가 일치하지 않습니다."),
    ORDER_NOT_FOUND("해당 주문을 찾지 못했습니다."),
    PRODUCT_NOT_FOUND("해당 상품을 찾지 못했습니다."),
    STORE_NOT_FOUND("해당 상점을 찾지 못했습니다."),
    CART_NOT_FOUND("해당 장바구니를 찾지 못했습니다."),
    CATEGORY_NOT_FOUND("해당 카테고리를 찾지 못했습니다."),
    PRODUCT_OUT_OF_STOCK("상품의 재고가 부족합니다. 현재 재고: "),
    ONE_DEPTH_NOT_FOUND("대분류 카테고리가 존재하지 않습니다."),
    TWO_DEPTH_NOT_FOUND("중분류 카테고리가 존재하지 않습니다."),
    INVALID_PAYMENT_AMOUNT("최소 주문 금액은 1000원 입니다."),
    ALREADY_APPROVED("이미 완료된 주문입니다."),
    PAYMENT_NOT_FOUND("결제 정보를 찾을 수 없습니다.");
    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
