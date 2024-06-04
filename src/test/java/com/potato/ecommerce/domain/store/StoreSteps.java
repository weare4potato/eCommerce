package com.potato.ecommerce.domain.store;

import com.potato.ecommerce.domain.store.entity.StoreEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

public class StoreSteps {

    public static StoreEntity createStore(final PasswordEncoder passwordEncoder) {
        return StoreEntity.builder()
            .id(1L)
            .password(passwordEncoder.encode("12345678"))
            .phone("01011112222")
            .email("test@email.com")
            .name("store")
            .description("hello")
            .businessNumber("1111111111")
            .build();
    }
}
