package com.potato.ecommerce.domain.store;

import static com.potato.ecommerce.domain.store.StoreSteps.createStoreRequestWithDiffPassword;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.potato.ecommerce.domain.store.dto.StoreRequest;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

public class StoreRequestTests {

    @Test
    @Transactional
    void 상점등록은_두_패스워드가_일치_해야만_한다() {
        // Arrange
        String password = "123456789";
        String diffPassword = "987654321";
        StoreRequest storeRequest = createStoreRequestWithDiffPassword(password, diffPassword);

        // Act + Assert
        assertThatThrownBy(() -> {
            storeRequest.validatePassword();
        }).isInstanceOf(ValidationException.class)
            .hasMessageContaining("비밀번호가 일치하지 않습니다.");
    }
}
