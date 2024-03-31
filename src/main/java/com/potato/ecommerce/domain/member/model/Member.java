package com.potato.ecommerce.domain.member.model;

import com.potato.ecommerce.domain.member.dto.ResponseMember;
import com.potato.ecommerce.domain.member.entity.UserRoleEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Builder
@NoArgsConstructor
public class Member {

    private Long id;
    private String email;
    private String userName;
    private String password;
    private String phone;
    private UserRoleEnum role;

    public Member(
        Long id,
        String email,
        String userName,
        String password,
        String phone,
        UserRoleEnum role
    ) {
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.role = role;
    }

    public ResponseMember createResponseDTO() {
        return ResponseMember.builder()
            .username(this.userName)
            .email(this.email)
            .phone(this.phone)
            .build();
    }

    public boolean isNotMatchPassword(PasswordEncoder encoder, String password) {
        return !encoder.matches(password, this.password);
    }

}
