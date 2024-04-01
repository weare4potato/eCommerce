package com.potato.ecommerce.domain.receiver.controller;

import com.potato.ecommerce.domain.receiver.dto.ReceiverForm;
import com.potato.ecommerce.domain.receiver.service.ReceiverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Receiver API", description = "Receiver API 입니다.")
@RequestMapping("/api/v1/users/receivers")
@Slf4j
public class ReceiverController {

    private final ReceiverService receiverService;

    @PostMapping
    @Tag(name = "Receiver API")
    @Operation(summary = "배송지 등록")
    public ResponseEntity<Void> createReceiver(
        @RequestBody @Validated ReceiverForm dto,
        HttpServletRequest request
    ){
        String subject = getSubject(request);

        receiverService.createReceiver(dto, subject);

        return ResponseEntity.status(201).build();
    }
    @GetMapping

    @PutMapping("/{receiverId}")

    @DeleteMapping("/{receiverId}")

    private String getSubject(HttpServletRequest request) {
        return (String) request.getAttribute("subject");
    }

}
