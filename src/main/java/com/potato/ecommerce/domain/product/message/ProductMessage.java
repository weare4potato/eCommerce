package com.potato.ecommerce.domain.product.message;

public class ProductMessage {

    private ProductMessage() {
    }

    public static final String PRODUCT_API = "Product API";
    public static final String PRODUCT_CREATE = "상품 등록";
    public static final String ALL_PRODUCT_LIST = "모든 상품 조회";
    public static final String PRODUCT_DETAIL = "상품 상세 조회";
    public static final String PRODUCT_LIST_BY_CATEGORY = "카테고리별 상품 조회";
    public static final String PRODUCT_LIST_BY_STORE = "상점별 상품 조회";

    public static final String PRODUCT_DELETE = "상품 삭제";
    public static final String PRODUCT_UPDATE = "상품 수정";
    public static final String PRODUCT_DELETE_SUCCESS = "상품 삭제 완료";


}
