package com.potato.ecommerce.domain.oauth.kakao.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoUserInfoDto {
    private Long id;
    private String nickname;
    private String email;
    private String phone;

    public KakaoUserInfoDto(Long id, String nickname, String email, String phone) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.phone = phone;
    }
}
