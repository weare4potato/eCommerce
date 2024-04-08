package com.potato.ecommerce.domain.member.message;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberMessage {

    public static final String UPDATE_PASSWORD = "비밀번호 변경이 완료되었습니다.";
    public static final String CONFIRM_AUTH = "인증이 완료되었습니다.";


}
