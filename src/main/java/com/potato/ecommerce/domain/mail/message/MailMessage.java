package com.potato.ecommerce.domain.mail.message;

public class MailMessage {
    public static final String RETURN_MESSAGE = "이메일 인증을 해주세요";
    public static final String EMAIL_SUBJECT = "회원가입 이메일 인증";
    public static final String AUTH_API = "http://localhost:8080/api/v1/users/signup/confirm?email=";
}
