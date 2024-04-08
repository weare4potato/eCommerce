package com.potato.ecommerce.domain.order.message;

public class OrderMessage {

    private OrderMessage() {
    }

    public static final String ORDER_API = "Order API";
    public static final String CREATE_ORDER = "주문 생성";
    public static final String GET_ORDER = "주문 단건 조회";
    public static final String GET_ORDERS = "주문 목록 조회";
    public static final String COMPLETE_ORDER = "주문 완료";
    public static final String CANCEL_ORDER = "주문 취소";
}
