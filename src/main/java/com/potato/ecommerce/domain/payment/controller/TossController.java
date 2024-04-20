package com.potato.ecommerce.domain.payment.controller;

import com.potato.ecommerce.domain.payment.dto.PaymentResponse;
import com.potato.ecommerce.domain.payment.dto.TossPaymentRequest;
import com.potato.ecommerce.domain.payment.service.TossService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Toss API")
@RequestMapping("/api/v1/payments")
public class TossController {

    private final TossService tossService;

    @PostMapping("/toss/confirm/{orderId}")
    @Operation(summary = "토스 결제")
    public ResponseEntity<PaymentResponse> createPayment(
        HttpServletRequest httpServletRequest,
        @RequestBody TossPaymentRequest tossPaymentRequest,
        @PathVariable String orderId
    ) throws BadRequestException {
        String subject = getSubject(httpServletRequest);
        return ResponseEntity.status(HttpStatus.OK)
            .body(tossService.createPayment(orderId, subject, tossPaymentRequest));
    }

    private String getSubject(HttpServletRequest request) {
        return (String) request.getAttribute("subject");
    }
}
