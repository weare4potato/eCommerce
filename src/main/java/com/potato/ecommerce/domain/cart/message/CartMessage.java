package com.potato.ecommerce.domain.cart.message;

public class CartMessage {

    private CartMessage() {

    }

    public static final String CART_API = "Cart API";
    public static final String ADD_CART = "장바구니 생성";
    public static final String UPDATE_CART = "장바구니 수정";
    public static final String DELETE_CART = "장바구니 삭제";
    public static final String GET_CARTS = "장바구니 조회";

}
