package com.potato.ecommerce.domain.store;

import com.potato.ecommerce.domain.store.dto.StoreRequest;
import com.potato.ecommerce.domain.store.entity.StoreEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

public class StoreSteps {

    public static StoreEntity createStore(final PasswordEncoder passwordEncoder) {
        return StoreEntity.builder()
            .id(1L)
            .password(passwordEncoder.encode("123456789"))
            .phone("01011112222")
            .email("test@email.com")
            .name("store")
            .description("hello")
            .businessNumber("1111111111")
            .build();
    }

    public static StoreRequest createStoreRequest() {
        final String email = "test@email.com";
        final String password = "123456789";
        final String validatePassword = "123456789";
        final String name = "name";
        final String description = "description";
        final String phone = "01011112222";
        final String businessNumber = "1111111111";
        return new StoreRequest(email, password, validatePassword, name,
            description, phone, businessNumber);
    }

    public static StoreRequest createStoreRequestWithDiffPassword(final String password,
        final String diffPassword) {
        final String email = "test@email.com";
        final String name = "name";
        final String description = "description";
        final String phone = "01011112222";
        final String businessNumber = "1111111111";
        return new StoreRequest(email, password, diffPassword, name,
            description, phone, businessNumber);
    }
}
