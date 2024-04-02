package com.potato.ecommerce.domain.mail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    @Autowired
    private final JavaMailSender javaMailSender;

    public void sendMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("vmfmgmfm57321@gmail.com");
        message.setSubject("하은땅... 힘내라능... 멀리서 지켜보고 있다능!!!");
        message.setText("Test");

        javaMailSender.send(message);
    }
}
