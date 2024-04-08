package com.potato.ecommerce.domain.receiver.message;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReceiverMessage {
    public static final String CREATE_RECEIVER = "배송지 생성";
    public static final String GET_RECEIVER = "배송지 조회";
    public static final String UPDATE_RECEIVER = "배송지 수정";
    public static final String DELETE_RECEIVER = "배송지 삭제";
    public static final String CREATE_MESSAGE = "배송지가 등록되었습니다.";

    public static final String DELETE_MESSAGE = "배송지가 삭제되었습니다.";
}
