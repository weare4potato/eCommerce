package com.potato.ecommerce.domain.store;

import static com.potato.ecommerce.global.exception.ExceptionMessage.BUSINESS_NUMBER_NOT_MATCH;
import static com.potato.ecommerce.global.exception.ExceptionMessage.EMAIL_NOT_MATCH;
import static com.potato.ecommerce.global.exception.ExceptionMessage.PASSWORD_NOT_MATCH;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.potato.ecommerce.domain.store.entity.StoreEntity;
import jakarta.xml.bind.ValidationException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class StoreTests {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Nested
    class Store_password_matches {

        @Test
        void same_password() {
            // Arrange
            StoreEntity store = StoreSteps.createStore(passwordEncoder);
            String requestPassword = "12345678";

            // Act + Assert
            store.passwordMatches(requestPassword, passwordEncoder);
        }

        @Test
        void diff_password() {
            // Arrange
            StoreEntity store = StoreSteps.createStore(passwordEncoder);
            String requestPassword = "987654321";

            // Act + Assert
            assertThatThrownBy(() -> {
                store.passwordMatches(requestPassword, passwordEncoder);
            }).isInstanceOf(
                ValidationException.class).hasMessageContaining(PASSWORD_NOT_MATCH.toString());
        }
    }

    @Nested
    class Store_email_matches {

        @Test
        void same_email() {
            // Arrange
            final StoreEntity store = StoreSteps.createStore(passwordEncoder);
            String requestEmail = "test@email.com";

            // Act + Assert
            store.emailMatches(requestEmail);
        }

        @Test
        void diff_email() {
            // Arrange
            final StoreEntity store = StoreSteps.createStore(passwordEncoder);
            String requestEmail = "diff@email.com";

            // Act
            assertThatThrownBy(() -> {
                store.emailMatches(requestEmail);
            }).isInstanceOf(
                ValidationException.class).hasMessageContaining(EMAIL_NOT_MATCH.toString());
        }
    }

    @Nested
    class Store_business_number_matches {

        @Test
        void save_business_number() {
            // Arrange
            final StoreEntity store = StoreSteps.createStore(passwordEncoder);
            String requestBusinessNumber = "1111111111";

            // Act + Assert
            store.businessNumberMatches(requestBusinessNumber);
        }

        @Test
        void diff_business_number() {
            // Arrange
            final StoreEntity store = StoreSteps.createStore(passwordEncoder);
            String requestBusinessNumber = "0000000000";

            // Act + Assert
            assertThatThrownBy(() -> {
                store.businessNumberMatches(requestBusinessNumber);
            }).isInstanceOf(
                    ValidationException.class)
                .hasMessageContaining(BUSINESS_NUMBER_NOT_MATCH.toString());
        }
    }

}
