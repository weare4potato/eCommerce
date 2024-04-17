package com.potato.ecommerce.domain.toss.entity;

import com.potato.ecommerce.domain.member.entity.MemberEntity;
import com.potato.ecommerce.domain.toss.dto.PaymentResponse;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", nullable = false, unique = true)
    private Long paymentId;

    @Column(nullable = false, name = "pay_type")
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Column(nullable = false, name = "pay_amount")
    private Long amount;

    @Column(nullable = false, name = "pay_name")
    private String orderName;

    @Column(nullable = false, name = "order_id")
    private String orderId;

    @Column(nullable = false)
    private String customerEmail;

    @Column(nullable = false)
    private String customerName;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer")
    private MemberEntity customer;

    @Builder
    public PaymentEntity(PaymentType paymentType, Long amount, String orderName, String orderId,
        String customerEmail, String customerName, LocalDateTime createdAt, MemberEntity customer) {
        this.paymentType = paymentType;
        this.amount = amount;
        this.orderName = orderName;
        this.orderId = orderId;
        this.customerEmail = customerEmail;
        this.customerName = customerName;
        this.createdAt = createdAt;
        this.customer = customer;
    }
}
