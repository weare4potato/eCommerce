package com.potato.ecommerce.domain.payment.repository;

import com.potato.ecommerce.domain.payment.entity.PaymentWebhook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentWebhookRepository extends JpaRepository<PaymentWebhook, Long> {

}
