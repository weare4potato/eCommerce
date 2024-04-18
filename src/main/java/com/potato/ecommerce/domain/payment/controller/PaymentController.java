package com.potato.ecommerce.domain.payment.controller;

import com.potato.ecommerce.domain.payment.dto.CommonResult;
import com.potato.ecommerce.domain.payment.dto.PaymentDto;
import com.potato.ecommerce.domain.payment.dto.PaymentReq;
import com.potato.ecommerce.domain.payment.dto.PaymentRes;
import com.potato.ecommerce.domain.payment.dto.PaymentResHandleDto;
import com.potato.ecommerce.domain.payment.dto.PaymentResHandleFailDto;
import com.potato.ecommerce.domain.payment.dto.SingleResult;
import com.potato.ecommerce.domain.payment.dto.TossVirtualDto;
import com.potato.ecommerce.domain.payment.dto.TossWebhookDto;
import com.potato.ecommerce.domain.payment.service.PaymentService;
import com.potato.ecommerce.domain.payment.service.ResponseService;
import com.potato.ecommerce.domain.payment.util.ListResult;
import com.potato.ecommerce.global.exception.dto.BusinessException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "결제 API")
@RequestMapping("/api/v1/payment")
@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final ResponseService responseService;
    private final int FAIL = -1;

    @PostMapping
    @Operation(summary = "결제 요청")
    public SingleResult<PaymentRes> requestPayments(
        @RequestBody PaymentReq paymentReq
    ) {
        try {
            return responseService.getSingleResult(
                paymentService.requestPayments(paymentReq)
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }

    @GetMapping("/success")
    @Operation(summary = "결제 성공 리다이렉트")
    public SingleResult<PaymentResHandleDto> requestFinalPayments(
        @RequestParam String paymentKey,
        @RequestParam String orderId,
        @RequestParam Long amount
    ) {
        try {
            paymentService.verifyRequest(paymentKey, orderId, amount);
            PaymentResHandleDto result = paymentService.requestFinalPayment(paymentKey, orderId,
                amount);

            return responseService.getSingleResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }

    @GetMapping("/fail")
    @Operation(summary = "결제 실패 리다이렉트")
    public SingleResult<PaymentResHandleFailDto> requestFail(
        @RequestParam(name = "code") String errorCode,
        @RequestParam(name = "message") String errorMsg,
        @RequestParam(name = "orderId") String orderId
    ) {
        try {
            return responseService.getSingleResult(
                paymentService.requestFail(errorCode, errorMsg, orderId)
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }

    @GetMapping("/all")
    @Operation(summary = "고객 별 결제내역 전체 조회")
    public ListResult<PaymentDto> getAllPayments(
        @RequestParam(name = "email") String email,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createDate").descending());
        try {
            return responseService.getListResult(
                paymentService.getAllPayments(email, pageRequest)
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }

    @GetMapping("/one")
    @Operation(summary = "고객 단건 결제내역 조회")
    public SingleResult<PaymentDto> getOnePayment(
        @RequestParam(name = "email") String email
    ) {
        try {
            return responseService.getSingleResult(
                paymentService.getOnePayment(email)
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }


    @PostMapping("/virtual/income")
    @Operation(summary = "가상계좌 입금 알림 콜백 처리")
    public CommonResult confirmVirtualAccountIncome(
        @RequestBody TossVirtualDto tossVirtualDto
    ) {
        try {
            paymentService.handleVirtualAccountIncome(tossVirtualDto);
            return responseService.getSuccessResult();
        } catch (Exception e) {
            return responseService.getFailResult(
                FAIL,
                e.getMessage()
            );
        }
    }

    @PostMapping("/webhook")
    @Operation(summary = "토스페이먼츠 웹 훅 처리")
    public CommonResult tossPaymentWebhook(
        @RequestBody TossWebhookDto webhookDto
    ) {
        try {
            paymentService.registTossPaymentWebhook(webhookDto);
            return responseService.getSuccessResult();
        } catch (NullPointerException npe) {
            return responseService.getSuccessResult();
        } catch (Exception e) {
            return responseService.getFailResult(
                FAIL,
                e.getMessage()
            );
        }
    }
}
