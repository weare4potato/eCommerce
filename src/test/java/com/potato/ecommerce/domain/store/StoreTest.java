package com.potato.ecommerce.domain.store;

import static org.assertj.core.api.Assertions.assertThat;

import com.potato.ecommerce.domain.store.entity.StoreEntity;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class StoreTest implements StoreTestUtil{

    @Test
    void Store를_update_할_수_있다(){
        // given
        StoreEntity storeEntity = StoreEntity.builder()
            .email(TEST_STORE_EMAIL)
            .name(TEST_STORE_NAME)
            .password(TEST_STORE_PASSWORD)
            .phone(TEST_STORE_PHONE)
            .description(TEST_STORE_DESCRIPTION)
            .businessNumber(TEST_BUSINESS_NUMBER)
            .build();



        // when
        storeEntity.update(
            ANOTHER_PREFIX + TEST_STORE_NAME,
            ANOTHER_PREFIX + TEST_STORE_DESCRIPTION,
            TEST_ANOTHER_STORE_PHONE
        );

        // then
        assertThat(storeEntity.getName()).isEqualTo(ANOTHER_PREFIX + TEST_STORE_NAME);
        assertThat(storeEntity.getDescription()).isEqualTo(ANOTHER_PREFIX + TEST_STORE_DESCRIPTION);
        assertThat(storeEntity.getPhone()).isEqualTo(TEST_ANOTHER_STORE_PHONE);
    }

    @Test
    void Store는_비밀번호가_일치하는지_확인할_수_있다(){
        // given
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        StoreEntity storeEntity = StoreEntity.builder()
            .password(passwordEncoder.encode(TEST_STORE_PASSWORD))
            .build();

        // when + then
        assertThat(storeEntity.passwordMatches(TEST_STORE_PASSWORD, passwordEncoder)).isTrue();
    }

    @Test
    void Store는_Email이_일치하는지_확인할_수_있다(){
        // given
        StoreEntity storeEntity = StoreEntity.builder()
            .email(TEST_STORE_EMAIL)
            .build();

        // when + then
        assertThat(storeEntity.emailMatches(TEST_STORE_EMAIL)).isTrue();
    }

    @Test
    void Store는_BusinessNumber가_일치하는지_확인할_수_있다(){
        // given
        StoreEntity storeEntity = StoreEntity.builder()
            .businessNumber(TEST_BUSINESS_NUMBER)
            .build();

        // when + then
        assertThat(storeEntity.businessNumberMatches(TEST_BUSINESS_NUMBER)).isTrue();
    }
}
