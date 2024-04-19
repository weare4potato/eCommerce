package com.potato.ecommerce.domain.payment.service;

import static com.potato.ecommerce.domain.payment.dto.PayType.CARD;
import static com.potato.ecommerce.domain.payment.dto.PayType.VIRTUAL_ACCOUNT;
import static com.potato.ecommerce.domain.payment.util.ExMessage.PAYMENT_ERROR_ORDER_NOTFOUND;

import com.google.gson.Gson;
import com.potato.ecommerce.domain.member.repository.MemberJpaRepository;
import com.potato.ecommerce.domain.payment.dto.CancelPaymentReq;
import com.potato.ecommerce.domain.payment.dto.CancelPaymentRes;
import com.potato.ecommerce.domain.payment.dto.PayType;
import com.potato.ecommerce.domain.payment.dto.PaymentResHandleDto;
import com.potato.ecommerce.domain.payment.dto.REFUND_BANK_TYPE;
import com.potato.ecommerce.domain.payment.dto.TossErrorDto;
import com.potato.ecommerce.domain.payment.entity.CancelPayment;
import com.potato.ecommerce.domain.payment.entity.Payment;
import com.potato.ecommerce.domain.payment.repository.CancelPaymentRepository;
import com.potato.ecommerce.domain.payment.repository.PaymentRepository;
import com.potato.ecommerce.global.exception.dto.BusinessException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class CancelPaymentService {

    private final PaymentRepository paymentRepository;
    private final MemberJpaRepository memberRepository;
    private final CancelPaymentRepository cancelPaymentRepository;

    @Value("${payments.toss.test_client_api_key}")
    private String testClientApiKey;

    @Value("${payments.toss.test_secret_api_key}")
    private String testSecretApiKey;

    @Value("${payments.toss.origin_url}")
    private String tossOriginUrl;

    @Transactional
    public void requestPaymentCancel(String email, CancelPaymentReq cancelPaymentReq) {

        // 토스페이먼츠에게 취소 요청
        String paymentKey = cancelPaymentReq.getPaymentKey();
        String cancelReason = cancelPaymentReq.getCancelReason();
        // 고객 환불 은행 및 계좌
        REFUND_BANK_TYPE refundBankType = cancelPaymentReq.getBank();
        String refundAccount = cancelPaymentReq.getAccountNumber();
        Payment payment = paymentRepository.findByPaymentKey(paymentKey)
            .orElseThrow(() -> new BusinessException(PAYMENT_ERROR_ORDER_NOTFOUND));

        RestTemplate rest = new RestTemplate();

        URI uri = URI.create(tossOriginUrl + "/payments/" + paymentKey + "/cancel");

        HttpHeaders headers = new HttpHeaders();
        byte[] secretKeyByte = (testSecretApiKey + ":").getBytes(StandardCharsets.UTF_8);
        headers.setBasicAuth(new String(Base64.getEncoder().encode(secretKeyByte)));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        JSONObject param = new JSONObject();
        param.put("cancelReason", cancelReason);

        if (payment.getPayType().equals(VIRTUAL_ACCOUNT)) {
            param.put("cancelAmount", payment.getAmount());
            param.put("refundReceiveAccount", cancelPaymentReq.getRefundAccountDto());
        }

        PaymentResHandleDto paymentCancelResDto;
        try {
            paymentCancelResDto = rest.postForObject(
                uri,
                new HttpEntity<>(param, headers),
                PaymentResHandleDto.class
            );
        } catch (Exception e) {
            String errorResponse = e.getMessage().split(": ")[1];
            String errorMessage = new Gson()
                .fromJson(
                    errorResponse.substring(1, errorResponse.length() - 1),
                    TossErrorDto.class
                ).getMessage();
            throw new BusinessException(errorMessage);
        }

        if (paymentCancelResDto == null) {
            throw new BusinessException("응답값이 비어있습니다.");
        }

        Long cancelAmount = paymentCancelResDto.getCancels()[0].getCancelAmount();
        try {
            cancelPaymentSave(
                payment.getPayType(), paymentKey, paymentCancelResDto,
                cancelAmount, refundBankType, refundAccount
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }

    private void cancelPaymentSave(
        PayType payType, String paymentKey, PaymentResHandleDto paymentCancelResDto,
        Long cancelAmount, REFUND_BANK_TYPE refundBank, String refundAccount
    ) {
        paymentRepository
            .findByPaymentKey(paymentKey)
            .filter(P -> P.getAmount().equals(cancelAmount))
            .ifPresentOrElse(P -> {
                log.info("[결제 취소 고객 이력에 추가]");
                CancelPayment cancelPayment;
                if (payType.equals(CARD)) {
                    cancelPayment = paymentCancelResDto.toCancelPaymentByCard();
                } else {
                    cancelPayment = paymentCancelResDto.toCancelPaymentByVirtual(
                        refundBank.getBankName(), refundAccount);
                }
                P.getCustomer().addCancelPayment(cancelPayment);
                log.info("[결제 취소 세팅]");
                P.getCustomer().getPayments()
                    .stream().filter(p -> p.getPaymentKey().equals(paymentKey))
                    .findFirst()
                    .orElseThrow(() -> new BusinessException(PAYMENT_ERROR_ORDER_NOTFOUND))
                    .setCancelYn("Y");
                log.info("[결제 취소 완료]");
            }, () -> {
                throw new BusinessException(PAYMENT_ERROR_ORDER_NOTFOUND);
            });
    }

    @Transactional(readOnly = true)
    public List<CancelPaymentRes> getAllCancelPayments(String memberEmail,
        PageRequest pageRequest) {
        return cancelPaymentRepository
            .findAllByCustomerEmail(memberEmail, pageRequest)
            .stream().map(CancelPayment::toDto)
            .collect(Collectors.toList());
    }
}
