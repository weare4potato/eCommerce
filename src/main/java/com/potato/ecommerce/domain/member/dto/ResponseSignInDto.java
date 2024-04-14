package com.potato.ecommerce.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ResponseSignInDto {

    ResponseMember member;
    String message;

    public static ResponseSignInDto from(ResponseMember member, String message) {
        return ResponseSignInDto.builder().member(member).message(message).build();
    }
}
