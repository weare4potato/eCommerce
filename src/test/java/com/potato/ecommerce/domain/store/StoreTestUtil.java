package com.potato.ecommerce.domain.store;

import com.potato.ecommerce.domain.member.model.Member;
import com.potato.ecommerce.domain.store.dto.StoreRequest;
import com.potato.ecommerce.domain.store.model.Store;

public interface StoreTestUtil {
    String ANOTHER_PREFIX = "another";
    Long TEST_STORE_ID = 1L;
    Long TEST_ANOTHER_STORE_ID = 2L;
    String TEST_STORE_NAME = "storeName";
    String TEST_STORE_PASSWORD = "!password";
    String TEST_ANOTHER_STORE_PASSWORD = "@password";
    String TEST_STORE_EMAIL = "email@email.com";
    String TEST_STORE_PHONE = "01044443333";
    String TEST_ANOTHER_STORE_PHONE = "01033334444";
    String TEST_STORE_DESCRIPTION = "description";
    String TEST_BUSINESS_NUMBER = "0000000001";
    String TEST_ANOTHER_BUSINESS_NUMBER = "0000000002";
    String TEST_TOKEN = "token";
    String TEST_FAIL_TOKEN = "failToken";
    Store TEST_STORE = Store.builder()
        .name(TEST_STORE_NAME)
        .email(TEST_STORE_EMAIL)
        .password(TEST_STORE_PASSWORD)
        .description(TEST_STORE_DESCRIPTION)
        .phone(TEST_STORE_PHONE)
        .businessNumber(TEST_BUSINESS_NUMBER)
        .build();

    Store TEST_ANOTHER_STORE = Store.builder()
        .name(ANOTHER_PREFIX + TEST_STORE_NAME)
        .email(ANOTHER_PREFIX + TEST_STORE_EMAIL)
        .phone(ANOTHER_PREFIX + TEST_STORE_PHONE)
        .businessNumber(TEST_ANOTHER_BUSINESS_NUMBER)
        .build();

    StoreRequest TEST_STORE_REQUEST = new StoreRequest(
        TEST_STORE_EMAIL,
        TEST_STORE_PASSWORD,
        TEST_STORE_PASSWORD,
        TEST_STORE_NAME,
        TEST_STORE_DESCRIPTION,
        TEST_STORE_PHONE,
        TEST_BUSINESS_NUMBER
    );
}
