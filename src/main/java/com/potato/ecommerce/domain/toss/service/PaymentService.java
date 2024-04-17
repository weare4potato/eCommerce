package com.potato.ecommerce.domain.toss.service;

import static com.potato.ecommerce.global.exception.ExceptionMessage.ALREADY_APPROVED;
import static com.potato.ecommerce.global.exception.ExceptionMessage.INVALID_PAYMENT_AMOUNT;
import static com.potato.ecommerce.global.exception.ExceptionMessage.MEMBER_NOT_FOUND;
import static com.potato.ecommerce.global.exception.ExceptionMessage.PAYMENT_NOT_FOUND;

import com.potato.ecommerce.domain.member.entity.MemberEntity;
import com.potato.ecommerce.domain.member.repository.MemberJpaRepository;
import com.potato.ecommerce.domain.toss.config.TossPaymentConfig;
import com.potato.ecommerce.domain.toss.dto.PaymentResponse;
import com.potato.ecommerce.domain.toss.dto.PaymentSuccessResponse;
import com.potato.ecommerce.domain.toss.entity.PaymentEntity;
import com.potato.ecommerce.domain.toss.repository.PaymentRepository;
import com.potato.ecommerce.global.exception.ExceptionMessage;
import com.potato.ecommerce.global.exception.custom.PaymentException;
import jakarta.persistence.EntityNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final TossPaymentConfig tossPaymentConfig;

    @Transactional
    public PaymentResponse requestTossPayment(PaymentEntity paymentEntity, String subject) {
        MemberEntity memberEntity = memberJpaRepository.findByEmail(subject).orElseThrow(
            () -> new EntityNotFoundException(MEMBER_NOT_FOUND.toString())
        );

        if (paymentEntity.getAmount() < 1000) {
            throw new PaymentException(INVALID_PAYMENT_AMOUNT.toString());
        }
        paymentEntity.updateCustomer(memberEntity);
        return paymentRepository.save(paymentEntity).toPaymentResponse();
    }

    @Transactional
    public PaymentSuccessResponse tossPaymentSuccess(String paymentKey, String orderId,
        Long amount) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = getHeaders();
        JSONObject params = new JSONObject();
        params.put("orderId", orderId);
        params.put("amount", amount);

        PaymentSuccessResponse result = null;
        try {
            result = restTemplate.postForObject(TossPaymentConfig.URL + paymentKey,
                new HttpEntity<>(params, headers),
                PaymentSuccessResponse.class);
        } catch (Exception e) {
            throw new PaymentException(ALREADY_APPROVED.toString());
        }

        return result;
    }

    @Transactional
    public void tossPaymentFail(String code, String message, String orderId) {
        PaymentEntity paymentEntity = paymentRepository.findByOrderId(orderId).orElseThrow(
            () -> new PaymentException(ExceptionMessage.PAYMENT_NOT_FOUND.toString())
        );
        paymentEntity.updatePaySuccessYN(false);
        paymentEntity.updateFailReason(message);
    }

    @Transactional
    public Map cancelPaymentPoint(String subject, String paymentKey, String cancelReason) {
        PaymentEntity paymentEntity = paymentRepository.findByPaymentKeyAndCustomer_Email(
            paymentKey, subject).orElseThrow(
            () -> new PaymentException(PAYMENT_NOT_FOUND.toString())
        );

        paymentEntity.updateCancelYN(true);
        paymentEntity.updateCancelReason(cancelReason);
        return tossPaymentCancel(paymentKey, cancelReason);
    }

    public Slice<PaymentEntity> findAllChargingHistories(String subject, Pageable pageable) {
        boolean b = memberJpaRepository.existsByEmail(subject);
        if (!b) {
            throw new EntityNotFoundException(MEMBER_NOT_FOUND.toString());
        }
        return paymentRepository.findAllByCustomer_Email(
            subject,
            PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.Direction.DESC,
                "paymentId"
            )
        );
    }

    private Map tossPaymentCancel(String paymentKey, String cancelReason) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = getHeaders();
        JSONObject params = new JSONObject();
        params.put("cancelReason", cancelReason);

        return restTemplate.postForObject(TossPaymentConfig.URL + paymentKey + "/cancel",
            new HttpEntity<>(params, headers),
            Map.class);
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        String encodedAuthKey = new String(
            Base64.getEncoder().encode((tossPaymentConfig.getTestSecretKey() + ":").getBytes(
                StandardCharsets.UTF_8))
        );
        headers.setBasicAuth(encodedAuthKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }
}
