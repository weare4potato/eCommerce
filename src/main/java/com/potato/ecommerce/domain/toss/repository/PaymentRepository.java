package com.potato.ecommerce.domain.toss.repository;

import com.potato.ecommerce.domain.toss.entity.PaymentEntity;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    Optional<PaymentEntity> findByOrderId(String orderId);

    Optional<PaymentEntity> findByPaymentKeyAndCustomer_Email(String paymentKey, String email);

    Slice<PaymentEntity> findAllByCustomer_Email(String email, Pageable pageable);
}
