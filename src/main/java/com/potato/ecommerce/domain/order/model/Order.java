package com.potato.ecommerce.domain.order.model;

import com.potato.ecommerce.domain.member.model.Member;
import com.potato.ecommerce.domain.order.entity.OrderStatus;
import com.potato.ecommerce.domain.payment.model.Payment;
import com.potato.ecommerce.domain.receiver.model.Receiver;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Order {

    private Long id;

    private Member member;

    private Receiver receiver;

    private Payment payment;

    private String orderNum;

    private OrderStatus status;

    private LocalDateTime orderedAt;

    @Builder
    private Order(Long id, Member member, Receiver receiver, Payment payment, String orderNum,
        OrderStatus status) {
        this.id = id;
        this.member = member;
        this.receiver = receiver;
        this.payment = payment;
        this.orderNum = orderNum;
        this.status = status;
        this.orderedAt = LocalDateTime.now();
    }

//    public static Order from(final OrderRequestDto orderRequestDto){
//        return Order.builder()
//            .id(orderRequestDto.getId())
//            .member(orderRequestDto.getMember())
//            .receiver(orderRequestDto.getReceiver())
//            .payment(orderRequestDto.getPayment())
//            .orderNum(orderRequestDto.getOrderNum())
//            .status(orderRequestDto.getStatus())
//            .build();
//    }

}
