package com.potato.ecommerce.domain.payment.repository;

import com.potato.ecommerce.domain.payment.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

}
