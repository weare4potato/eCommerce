package com.potato.ecommerce.domain.member.dto;

import com.potato.ecommerce.domain.member.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseMember {

    private String username;
    private String email;
    private String phone;


    public static ResponseMember fromEntity(MemberEntity member) {
        return ResponseMember.builder()
            .username(member.getUserName())
            .email(member.getEmail())
            .phone(member.getPhone())
            .build();
    }
}
