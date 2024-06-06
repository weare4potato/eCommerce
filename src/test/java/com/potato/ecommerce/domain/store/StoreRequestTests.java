package com.potato.ecommerce.domain.store;

import static com.potato.ecommerce.domain.store.StoreSteps.createStoreRequestWithDiffPassword;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.potato.ecommerce.domain.store.dto.StoreRequest;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class StoreRequestTests {

    @Nested
    class store_request_validate_password {

        @Test
        void same_password() {
            // Arrange
            String password = "123456789";
            String diffPassword = "123456789";
            StoreRequest storeRequest = createStoreRequestWithDiffPassword(password, diffPassword);

            // Act + Assert
            storeRequest.validatePassword();
        }

        @Test
        void diff_password() {
            // Arrange
            String password = "123456789";
            String diffPassword = "987654321";
            StoreRequest storeRequest = createStoreRequestWithDiffPassword(password, diffPassword);

            // Act + Assert
            assertThatThrownBy(storeRequest::validatePassword).isInstanceOf(
                    ValidationException.class)
                .hasMessageContaining("비밀번호가 일치하지 않습니다.");
        }
    }

}
