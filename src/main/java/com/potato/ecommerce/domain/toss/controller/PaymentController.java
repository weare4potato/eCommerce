package com.potato.ecommerce.domain.toss.controller;

import com.potato.ecommerce.domain.toss.entity.PaymentEntity;
import com.potato.ecommerce.domain.toss.dto.PaymentFailDto;
import com.potato.ecommerce.domain.toss.mapper.PaymentMapper;
import com.potato.ecommerce.domain.toss.dto.PaymentRequest;
import com.potato.ecommerce.domain.toss.dto.PaymentResponse;
import com.potato.ecommerce.domain.toss.service.PaymentService;
import com.potato.ecommerce.domain.toss.dto.PaymentSuccessResponse;
import com.potato.ecommerce.domain.toss.dto.SliceInfoResponse;
import com.potato.ecommerce.domain.toss.dto.SliceResponse;
import com.potato.ecommerce.domain.toss.config.TossPaymentConfig;
import com.potato.ecommerce.global.exception.dto.BusinessException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@Tag(name = "Payment API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/{orderId}/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final TossPaymentConfig tossPaymentConfig;
    private final PaymentMapper paymentMapper;

    @PostMapping
    @Operation(summary = "결제 요청")
    public ResponseEntity<PaymentResponse> requestPayments(
        HttpServletRequest httpServletRequest,
        @RequestBody PaymentRequest paymentRequest
    ) {
        try {
            return ResponseEntity.ok().body(paymentService.requestPayment(paymentRequest));
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }

    @GetMapping("/toss/success")
    @Operation(summary = "결제 성공")
    public ResponseEntity<PaymentSuccessResponse> requestFinalPayments(
        @RequestParam String paymentKey,
        @RequestParam String orderId,
        @RequestParam Long amount
    ) {
        PaymentSuccessResponse paymentSuccessResponse = paymentService.tossPaymentSuccess(
            paymentKey, orderId, amount);
        return ResponseEntity.ok().body(paymentSuccessResponse);
    }

    @GetMapping("/toss/fail")
    public ResponseEntity tossPaymentFail(
        @RequestParam String code,
        @RequestParam String message,
        @RequestParam String orderId
    ) {
        paymentService.tossPaymentFail(code, message, orderId);
        return ResponseEntity.ok().body(
            PaymentFailDto.builder()
                .errorCode(code)
                .errorMessage(message)
                .orderId(orderId)
                .build()
        );
    }

    @PostMapping("/toss/cancel/point")
    public ResponseEntity tossPaymentCancelPoint(
        HttpServletRequest httpServletRequest,
        @RequestParam String paymentKey,
        @RequestParam String cancelReason
    ) {
        String subject = getSubject(httpServletRequest);
        Map map = paymentService.cancelPaymentPoint(subject, paymentKey, cancelReason);
        return ResponseEntity.ok().body(map);
    }

    @GetMapping("/history")
    public ResponseEntity getChargingHistory(
        HttpServletRequest httpServletRequest,
        Pageable pageable
    ) {
        String subject = getSubject(httpServletRequest);
        Slice<PaymentEntity> chargingHistories = paymentService.findAllChargingHistories(subject,
            pageable);
        SliceInfoResponse sliceInfoResponse = new SliceInfoResponse(pageable, chargingHistories.getNumberOfElements(),
            chargingHistories.hasNext());

        return new ResponseEntity<>(
            new SliceResponse<>(paymentMapper.chargingHistoryToChargingHistoryResponse(
                chargingHistories.getContent()), sliceInfoResponse), HttpStatus.OK);
    }

    private String getSubject(HttpServletRequest request) {
        return (String) request.getAttribute("subject");
    }
}
