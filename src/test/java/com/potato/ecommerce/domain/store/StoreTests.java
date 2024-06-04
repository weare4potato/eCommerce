package com.potato.ecommerce.domain.store;

import static org.assertj.core.api.Assertions.assertThat;

import com.potato.ecommerce.domain.store.entity.StoreEntity;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class StoreTests {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Nested
    class Store_password_matches {
        @Test
        void same_password() {
            // Arrange
            StoreEntity store = createStore(passwordEncoder);
            String requestPassword = "12345678";

            // Act
            final boolean isMatches = store.passwordMatches(requestPassword, passwordEncoder);

            // Assert
            assertThat(isMatches).isTrue();
        }

        @Test
        void diff_password() {
            // Arrange
            StoreEntity store = createStore(passwordEncoder);
            String requestPassword = "987654321";

            // Act
            final boolean isMatches = store.passwordMatches(requestPassword, passwordEncoder);

            // Assert
            assertThat(isMatches).isFalse();
        }
    }

    @Nested
    class Store_smail_matches {

        @Test
        void same_email() {
            // Arrange
            final StoreEntity store = createStore(passwordEncoder);
            String requestEmail = "test@email.com";

            // Act
            final boolean isMatches = store.emailMatches(requestEmail);

            // Assert
            assertThat(isMatches).isTrue();
        }

        @Test
        void diff_email() {
            // Arrange
            final StoreEntity store = createStore(passwordEncoder);
            String requestEmail = "diff@email.com";

            // Act
            final boolean isMatches = store.emailMatches(requestEmail);

            // Assert
            assertThat(isMatches).isFalse();
        }
    }

    @Nested
    class Store_business_number_matches {

        @Test
        void save_business_number() {
            // Arrange
            final StoreEntity store = createStore(passwordEncoder);
            String requestBusinessNumber = "1111111111";

            // Act
            final boolean isMatches = store.businessNumberMatches(requestBusinessNumber);

            // Assert
            assertThat(isMatches).isTrue();
        }

        @Test
        void diff_business_number() {
            // Arrange
            final StoreEntity store = createStore(passwordEncoder);
            String requestBusinessNumber = "0000000000";

            // Act
            final boolean isMatches = store.businessNumberMatches(requestBusinessNumber);

            // Assert
            assertThat(isMatches).isFalse();
        }
    }

    private static StoreEntity createStore(final PasswordEncoder passwordEncoder) {
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
