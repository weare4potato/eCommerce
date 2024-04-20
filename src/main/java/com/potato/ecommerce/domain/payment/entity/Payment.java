package com.potato.ecommerce.domain.payment.entity;

import com.potato.ecommerce.domain.member.entity.MemberEntity;
import com.potato.ecommerce.domain.payment.dto.PayType;
import com.potato.ecommerce.domain.payment.dto.TossPayment;
import com.potato.ecommerce.domain.product.entity.ProductEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "Payment")
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @Column
    private Integer price;

    @Column
    private String payType;

    @Column
    private String paidAt;

    @Column
    private String method;

    @Column
    private String orderId;

//    @Column
//    private String provider;

//    @Column
//    private String receipt;

    @Builder
    public Payment(MemberEntity member, Integer price, String payType,
        String paidAt, String method, String orderId) {
        this.member = member;
        this.price = price;
        this.payType = payType;
        this.paidAt = paidAt;
        this.method = method;
        this.orderId = orderId;
//        this.provider = provider;
//        this.receipt = receipt;
    }
}
