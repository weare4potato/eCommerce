package com.potato.ecommerce.domain.payment.repository;

import com.potato.ecommerce.domain.payment.entity.Payment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findAllByMemberId(Long id);
}
