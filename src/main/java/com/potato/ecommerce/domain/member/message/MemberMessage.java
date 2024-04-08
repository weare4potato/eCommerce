package com.potato.ecommerce.domain.member.message;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberMessage {

    public static final String MEMBER_API = "Member API";
    public static final String SING_UP = "회원가입";
    public static final String SING_IN = "로그인";
    public static final String SING_OUT = "로그아웃";
    public static final String GET_MEMBER = "단일 조회";
    public static final String PASSWORD_CHECK = "비밀번호 확인";
    public static final String UPDATE_MEMBER = "정보 수정";
    public static final String UPDATE_PASSWORD = "비밀번호 수정";


    public static final String UPDATE_PASSWORD_MESSAGE = "비밀번호 변경이 완료되었습니다.";
    public static final String CONFIRM_AUTH = "인증이 완료되었습니다.";


}
