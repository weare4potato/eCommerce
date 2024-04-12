package com.potato.ecommerce.domain.member.dto;

import lombok.Builder;

@Builder
public class ResponseSignInDto {

    ResponseMember member;
    String message;

    public static ResponseSignInDto from(ResponseMember member, String message) {
        return ResponseSignInDto.builder().member(member).message(message).build();
    }
}
