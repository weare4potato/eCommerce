package com.potato.ecommerce.domain.payment.service;

import static com.potato.ecommerce.global.exception.ExceptionMessage.MEMBER_NOT_FOUND;

import com.potato.ecommerce.domain.member.entity.MemberEntity;
import com.potato.ecommerce.domain.member.repository.MemberJpaRepository;
import com.potato.ecommerce.domain.order.entity.OrderEntity;
import com.potato.ecommerce.domain.order.repository.order.OrderJpaRepository;
import com.potato.ecommerce.domain.payment.dto.PaymentResponse;
import com.potato.ecommerce.domain.payment.dto.TossPayment;
import com.potato.ecommerce.domain.payment.dto.TossPaymentRequest;
import com.potato.ecommerce.domain.payment.entity.Payment;
import com.potato.ecommerce.domain.payment.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class TossService {

    public static final String URL = "https://api.tosspayments.com/v1/payments/confirm";
    private final MemberJpaRepository memberRepository;
    private final OrderJpaRepository orderRepository;
    private final PaymentRepository paymentRepository;
    @Value("${payments.toss.test_secret_api_key}")
    private String tossSecretKey;

    @Transactional
    public PaymentResponse createPayment(String orderName, String subject,
        TossPaymentRequest tossPaymentRequest)        //paymentKey, orderId, amount
        throws BadRequestException {
        log.info("orderId : " + tossPaymentRequest.getOrderId());
        MemberEntity memberEntity = memberRepository.findByEmail(subject).orElseThrow(
            () -> new EntityNotFoundException(MEMBER_NOT_FOUND.toString())
        );

        OrderEntity order = getOrder(orderName);
        isAuthorized(order, memberEntity);
        String authorization = Base64.getEncoder()
            .encodeToString(tossSecretKey.getBytes());    //TOSS_SECRET Base64 인코딩
        RestTemplate restTemplate = new RestTemplate();

        //헤더구성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + authorization);     //TOSS_SECRET
        headers.setContentType(MediaType.APPLICATION_JSON);

        //요청 객체 생성
        HttpEntity<TossPaymentRequest> requestHttpEntity = new HttpEntity<>(tossPaymentRequest,
            headers);

        //응답 객체 TossPayment객체로 결제 응답받기
        TossPayment tossPayment = restTemplate.postForObject(URL, requestHttpEntity,
            TossPayment.class);

        Payment payment = Payment
            .builder()
            .member(memberEntity)
            .price(tossPayment.getTotalAmount())
            .payType(tossPayment.getType())
            .paidAt(tossPayment.getApprovedAt())
            .method(tossPayment.getMethod())
            .orderId(tossPayment.getOrderId())
            .build();
            //결제 응답에서 필요한 부분만 사용하여 업데이트(receipt, paidAt, method, orderId, provider)
        return new PaymentResponse(paymentRepository.save(payment));
    }

    private OrderEntity getOrder(String orderName) throws BadRequestException {
        return orderRepository.findByOrderNum(orderName).orElseThrow(
            () -> new BadRequestException("해당 결제정보가 존재하지 않습니다.")
        );
    }

    private void isAuthorized(OrderEntity order, MemberEntity member) throws BadRequestException {
        if (!order.getMember().getId().equals(member.getId())) {
            throw new BadRequestException("해당 결제를 진핼할 권한이 없습니다.");
        }
    }
}
