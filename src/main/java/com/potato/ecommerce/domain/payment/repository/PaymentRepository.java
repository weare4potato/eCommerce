package com.potato.ecommerce.domain.payment.repository;

import com.potato.ecommerce.domain.payment.entity.Payment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByOrderId(String orderId);

    Optional<Payment> findByPaymentKey(String paymentKey);

    List<Payment> findAllByCustomerEmail(String email, Pageable pageable);

    Optional<Payment> findByCustomerEmail(String customerEmail);
}
