package com.potato.ecommerce.domain.mail.service;

import static com.potato.ecommerce.domain.mail.message.MailMessage.*;

import com.potato.ecommerce.domain.mail.message.MailMessage;
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

        return RETURN_MESSAGE;
    }


    private SimpleMailMessage createMessage(String email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(EMAIL_SUBJECT);
        message.setText("이메일 인증\n"
            + "아래 링크를 클릭해주시면 이메일 인증이 완료됩니다.\n"
            + AUTH_API
            + email
        );
        return message;
    }
}
