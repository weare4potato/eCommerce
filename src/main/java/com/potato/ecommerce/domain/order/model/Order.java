package com.potato.ecommerce.domain.order.model;

import com.potato.ecommerce.domain.member.model.Member;
import com.potato.ecommerce.domain.order.entity.OrderEntity;
import com.potato.ecommerce.domain.order.entity.OrderStatus;
import com.potato.ecommerce.domain.payment.vo.PaymentType;
import com.potato.ecommerce.domain.receiver.model.Receiver;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Order {

    private final Long id;

    private final Member member;

    private final Receiver receiver;

    private final PaymentType payment;

    private final String orderNum;

    private final OrderStatus status;
    private final Long totalPrice;

    private final LocalDateTime orderedAt;

    @Builder
    private Order(
        Long id,
        Member member, Receiver receiver, PaymentType payment, String orderNum,
        OrderStatus status, Long totalPrice, LocalDateTime orderedAt) {
        this.id = id;
        this.member = member;
        this.receiver = receiver;
        this.payment = payment;
        this.orderNum = orderNum;
        this.status = status;
        this.totalPrice = totalPrice;
        this.orderedAt = orderedAt;
    }

    public OrderEntity toEntity() {
        return OrderEntity.builder()
            .id(id)
            .member(member.toEntity())
            .receiver(receiver.toEntity())
            .paymentType(payment)
            .orderNum(orderNum)
            .status(status)
            .totalPrice(totalPrice)
            .orderedAt(orderedAt)
            .build();

    }

    public static Order fromEntity(OrderEntity orderEntity) {
        return Order.builder()
            .id(orderEntity.getId())
            .member(Member.fromEntity(orderEntity.getMember()))
            .receiver(Receiver.fromEntity(orderEntity.getReceiver()))
            .payment(orderEntity.getPaymentType())
            .orderNum(orderEntity.getOrderNum())
            .status(orderEntity.getStatus())
            .totalPrice(orderEntity.getTotalPrice())
            .orderedAt(orderEntity.getOrderedAt())
            .build();
    }

    public Order complete() {
        return Order.builder()
            .id(this.id)
            .member(this.member)
            .receiver(this.receiver)
            .payment(this.payment)
            .orderNum(this.orderNum)
            .status(OrderStatus.COMPLETE)
            .orderedAt(this.orderedAt)
            .totalPrice(this.totalPrice)
            .build();
    }

    public Order cancel() {
        return Order.builder()
            .id(this.id)
            .member(this.member)
            .receiver(this.receiver)
            .payment(this.payment)
            .orderNum(this.orderNum)
            .status(OrderStatus.CANCEL)
            .orderedAt(this.orderedAt)
            .totalPrice(this.totalPrice)
            .build();
    }
}
