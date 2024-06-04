package com.potato.ecommerce.domain.member;

import static org.assertj.core.api.Assertions.assertThat;

import com.potato.ecommerce.domain.member.entity.MemberEntity;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MemberTests {
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Nested
    class Member_password_matches {

        @Test
        void same_password() {
            // Arrange
            MemberEntity member = MemberSteps.createMemberWithPassword("123456789", passwordEncoder);

            String requestPassword = "123456789";

            // Act
            final boolean isMatches = member.isNotMatchPassword(passwordEncoder,
                requestPassword);

            // Assert
            assertThat(isMatches).isFalse();
        }

        @Test
        void diff_password() {
            // Arrange
            MemberEntity member = MemberSteps.createMemberWithPassword("123456789", passwordEncoder);

            String requestPassword = "987654321";

            // Act
            boolean isMatches = member.isNotMatchPassword(passwordEncoder,
                requestPassword);

            // Assert
            assertThat(isMatches).isTrue();
        }
    }

    @Nested
    class Member_valid_auth {

        @Test
        void check_member() {
            // Arrange
            MemberEntity member = MemberSteps.createMemberWithAuth(true, passwordEncoder);
            // Act
            boolean authCheck = member.isNotAuthCheck();

            // Assert
            assertThat(authCheck).isFalse();
        }

        @Test
        void un_check_member() {
            // Arrange
            MemberEntity member = MemberSteps.createMemberWithAuth(false, passwordEncoder);

            // Act
            boolean authCheck = member.isNotAuthCheck();

            // Assert
            assertThat(authCheck).isTrue();
        }
    }


}
