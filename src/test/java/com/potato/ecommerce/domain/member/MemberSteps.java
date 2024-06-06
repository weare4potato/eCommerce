package com.potato.ecommerce.domain.member;

import com.potato.ecommerce.domain.member.entity.MemberEntity;
import com.potato.ecommerce.domain.member.entity.UserRoleEnum;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MemberSteps {

    public static MemberEntity createMember(PasswordEncoder passwordEncoder) {
        return MemberEntity.builder()
            .email("test@email.com")
            .userName("testName")
            .password(passwordEncoder.encode("123456789"))
            .phone("01011112222")
            .role(UserRoleEnum.USER)
            .authStatus(true)
            .build();
    }

    public static MemberEntity createMemberWithPassword(String password,
        PasswordEncoder passwordEncoder) {
        return MemberEntity.builder()
            .email("test@email.com")
            .userName("testName")
            .password(passwordEncoder.encode(password))
            .phone("01011112222")
            .role(UserRoleEnum.USER)
            .authStatus(true)
            .build();
    }

    static MemberEntity createMemberWithAuth(boolean auth, PasswordEncoder passwordEncoder) {
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
