package com.potato.ecommerce.domain.mail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    public String sendMail(String email) throws MailException {
        javaMailSender.send(createMessage(email));

        return "이메일 인증을 해주세요";
    }


    private SimpleMailMessage createMessage(String email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("회원가입 이메일 인증");
        message.setText("이메일 인증\n"
            + "아래 링크를 클릭해주시면 이메일 인증이 완료됩니다.\n"
            + "http://localhost:8080/api/v1/users/signup/confirm?email="
            + email
        );
        return message;
    }
}
