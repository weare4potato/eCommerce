package com.potato.ecommerce.domain.mail.controller;

import com.potato.ecommerce.domain.mail.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mail")
public class MailController {

    private final MailService mailService;

    @PostMapping
    public ResponseEntity<Void> mailSend(){
        mailService.sendMail();

        return ResponseEntity.ok().build();
    }
}
