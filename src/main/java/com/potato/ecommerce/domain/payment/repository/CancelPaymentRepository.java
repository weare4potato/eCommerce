package com.potato.ecommerce.domain.payment.repository;

import com.potato.ecommerce.domain.payment.entity.CancelPayment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CancelPaymentRepository extends JpaRepository<CancelPayment, Long> {

    Optional<CancelPayment> findByPaymentKey(String orderId);

    List<CancelPayment> findAllByCustomerEmail(String memberEmail, Pageable pageable);
}
