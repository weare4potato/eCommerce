package com.potato.ecommerce.domain.order.entity;

import com.potato.ecommerce.domain.member.entity.MemberEntity;
import com.potato.ecommerce.domain.order.model.Order;
import com.potato.ecommerce.domain.payment.entity.PaymentEntity;
import com.potato.ecommerce.domain.receiver.entity.ReceiverEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private PaymentEntity payment;

    @Column(nullable = false)
    private String orderNum;

    /*
    TODO : 지금은 모든 주문을 완료상태로 처리
    TODO : READY는 추후에 뼈대를 다 만들고 추가
     */
    @Column(nullable = false)
    private OrderStatus status;

    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime orderedAt;

    @Builder
    public OrderEntity(MemberEntity member, ReceiverEntity receiver, PaymentEntity payment,
        String orderNum, OrderStatus status, LocalDateTime orderedAt) {
        this.member = member;
        this.receiver = receiver;
        this.payment = payment;
        this.orderNum = orderNum;
        this.status = status;
        this.orderedAt = orderedAt;
    }

    public static OrderEntity fromModel(Order order){
        return OrderEntity.builder()
            .member(MemberEntity.fromModel(order.getMember()))
            .receiver(ReceiverEntity.fromModel(order.getReceiver()))
            .payment(PaymentEntity.fromModel(order.getPayment()))
            .orderNum(order.getOrderNum())
            .status(order.getStatus())
            .build();
    }

//    public Order toModel(){
//        return Order.builder()
//            .id(id)
//            .member(member.toModel())
//            .receiver(receiver)
//            .payment(payment)
//            .orderNum(orderNum)
//            .status(status)
//            .orderedAt(orderedAt)
//            .build();
//    }
}
