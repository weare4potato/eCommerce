package com.potato.ecommerce.domain.member.model;

import com.potato.ecommerce.domain.member.dto.ResponseMember;
import com.potato.ecommerce.domain.member.dto.UpdateMemberDto;
import com.potato.ecommerce.domain.member.entity.MemberEntity;
import com.potato.ecommerce.domain.member.entity.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    private Long id;
    private String email;
    private String userName;
    private String password;
    private String phone;
    private UserRoleEnum role;
    private boolean authStatus;

    public static Member fromEntity(MemberEntity member) {
        return Member.builder()
            .id(member.getId())
            .email(member.getEmail())
            .userName(member.getUserName())
            .password(member.getPassword())
            .phone(member.getPhone())
            .role(member.getRole())
            .authStatus(member.isAuthStatus())
            .build();
    }

    public MemberEntity toEntity() {
        return MemberEntity.builder()
            .id(this.id)
            .email(this.email)
            .userName(this.userName)
            .password(this.password)
            .phone(this.phone)
            .role(this.role)
            .authStatus(this.authStatus)
            .build();
    }

    public void update(UpdateMemberDto dto) {
        this.userName = dto.getUsername();
        this.phone = dto.getPhone();
    }

    public void confirm() {
        this.authStatus = true;
    }


    public void updatePassword(String newPassword) {
        this.password = newPassword;
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

    public boolean isNotMatchMember(Long id) {
        return !this.id.equals(id);
    }

    public boolean isNotAuthCheck() {
        return !this.authStatus;
    }

}
