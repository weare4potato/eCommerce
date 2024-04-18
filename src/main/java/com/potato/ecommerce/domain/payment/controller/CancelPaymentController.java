package com.potato.ecommerce.domain.payment.controller;

import com.potato.ecommerce.domain.payment.dto.CancelPaymentReq;
import com.potato.ecommerce.domain.payment.dto.CancelPaymentRes;
import com.potato.ecommerce.domain.payment.dto.CommonResult;
import com.potato.ecommerce.domain.payment.service.CancelPaymentService;
import com.potato.ecommerce.domain.payment.service.ResponseService;
import com.potato.ecommerce.domain.payment.util.ListResult;
import com.potato.ecommerce.global.exception.dto.BusinessException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "결제 취소 API")
@RequestMapping("/v1/api/cancelPayment")
@RestController
@RequiredArgsConstructor
public class CancelPaymentController {

    private final ResponseService responseService;
    private final CancelPaymentService cancelPaymentService;
    private final int FAIL = -1;

    @PostMapping
    @Operation(summary = "결제 완료 건 결제취소")
    public CommonResult requestPaymentCancel(
        @RequestParam String email,
        @RequestBody CancelPaymentReq cancelPaymentReq
    ) {
        try {
            cancelPaymentService.requestPaymentCancel(email, cancelPaymentReq);
            return responseService.getSuccessResult();
        } catch (Exception e) {
            return responseService.getFailResult(
                -1,
                e.getMessage()
            );
        }
    }

    @GetMapping
    @Operation(summary = "취소 목록 전체 조회")
    public ListResult<CancelPaymentRes> getAllCancelPayments(
        @RequestParam String memberEmail,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("cancelDate").descending());
        try {
            return responseService.getListResult(
                cancelPaymentService.getAllCancelPayments(memberEmail, pageRequest)
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }
}
