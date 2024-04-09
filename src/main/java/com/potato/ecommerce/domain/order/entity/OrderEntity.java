package com.potato.ecommerce.domain.order.entity;

import com.potato.ecommerce.domain.member.entity.MemberEntity;
import com.potato.ecommerce.domain.payment.vo.PaymentType;
import com.potato.ecommerce.domain.receiver.entity.ReceiverEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private ReceiverEntity receiver;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType paymentType;

    @Column(nullable = false)
    private String orderNum;

    /*
    TODO : 지금은 모든 주문을 완료상태로 처리
    TODO : READY는 추후에 뼈대를 다 만들고 추가
     */

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Min(value = 0)
    @Column(nullable = false)
    private Long totalPrice;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime orderedAt;

    public OrderEntity(
        MemberEntity member,
        ReceiverEntity receiver,
        PaymentType paymentType,
        String orderNum,
        Long totalPrice
    ) {
        this.member = member;
        this.receiver = receiver;
        this.paymentType = paymentType;
        this.orderNum = orderNum;
        this.totalPrice = totalPrice;
        this.status = OrderStatus.READY;
    }

    public OrderEntity complete() {
        return OrderEntity.builder()
            .id(this.id)
            .member(this.member)
            .receiver(this.receiver)
            .paymentType(this.paymentType)
            .orderNum(this.orderNum)
            .status(OrderStatus.COMPLETE)
            .orderedAt(this.orderedAt)
            .totalPrice(this.totalPrice)
            .build();
    }

    public OrderEntity cancel() {
        return OrderEntity.builder()
            .id(this.id)
            .member(this.member)
            .receiver(this.receiver)
            .paymentType(this.paymentType)
            .orderNum(this.orderNum)
            .status(OrderStatus.CANCEL)
            .orderedAt(this.orderedAt)
            .totalPrice(this.totalPrice)
            .build();
    }

}
