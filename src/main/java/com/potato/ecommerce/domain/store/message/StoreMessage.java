package com.potato.ecommerce.domain.store.message;

public class StoreMessage {

    private StoreMessage() {
    }

    public static final String STORE_API = "Store API";
    public static final String STORE_SIGN_UP = "상점 등록";
    public static final String STORE_SIGN_IN = "상점 로그인";
    public static final String STORE_INFO = "판매자 상점 조회";
    public static final String STORE_UPDATE = "상점 수정";
    public static final String STORE_PASSWORD_VALIDATION = "비밀번호 확인";
    public static final String STORE_PASSWORD_VALIDATION_SUCCESS = "비밀번호 확인 성공";
    public static final String STORE_DELETE = "상점 탈퇴";
    public static final String STORE_DELETE_SUCCESS = "상점 탈퇴 완료";

}
