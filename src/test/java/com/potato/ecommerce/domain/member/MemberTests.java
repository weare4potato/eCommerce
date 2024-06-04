package com.potato.ecommerce.domain.member;

import static org.assertj.core.api.Assertions.assertThat;

import com.potato.ecommerce.domain.member.entity.MemberEntity;
import com.potato.ecommerce.domain.member.entity.UserRoleEnum;
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
            MemberEntity member = createMember("123456789");

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
            MemberEntity member = createMember("123456789");

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

            MemberEntity member = createAuthMember(true);
            // Act
            boolean authCheck = member.isNotAuthCheck();

            // Assert
            assertThat(authCheck).isFalse();
        }

        @Test
        void un_check_member() {
            // Arrange
            MemberEntity member = createAuthMember(false);

            // Act
            boolean authCheck = member.isNotAuthCheck();

            // Assert
            assertThat(authCheck).isTrue();
        }
    }



    private MemberEntity createMember(String password) {
        return MemberEntity.builder()
            .email("test@email.com")
            .userName("testName")
            .password(passwordEncoder.encode(password))
            .phone("01011112222")
            .role(UserRoleEnum.USER)
            .authStatus(true)
            .build();
    }

    private MemberEntity createAuthMember(boolean auth) {
        return MemberEntity.builder()
            .email("test@email.com")
            .userName("testName")
            .password(passwordEncoder.encode("123456789"))
            .phone("01011112222")
            .role(UserRoleEnum.USER)
            .authStatus(auth)
            .build();
    }
}
