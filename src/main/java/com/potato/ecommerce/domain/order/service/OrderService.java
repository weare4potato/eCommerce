package com.potato.ecommerce.domain.order.service;

import com.potato.ecommerce.domain.member.entity.MemberEntity;
import com.potato.ecommerce.domain.member.repository.MemberJpaRepository;
import com.potato.ecommerce.domain.order.dto.HistoryInfo;
import com.potato.ecommerce.domain.order.dto.OrderInfo;
import com.potato.ecommerce.domain.order.dto.OrderInfoWithHistory;
import com.potato.ecommerce.domain.order.dto.OrderProduct;
import com.potato.ecommerce.domain.order.entity.OrderEntity;
import com.potato.ecommerce.domain.order.repository.OrderRepository;
import com.potato.ecommerce.domain.payment.vo.PaymentType;
import com.potato.ecommerce.domain.receiver.entity.ReceiverEntity;
import com.potato.ecommerce.domain.receiver.repository.ReceiverJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final MemberJpaRepository memberJpaRepository;
    private final ReceiverJpaRepository receiverJpaRepository;
    private final OrderRepository orderRepository;
    private final HistoryService historyService;

    public OrderInfo createOrder(
        Long memberId,
        Long receiverId,
        PaymentType type,
        Long totalPrice,
        List<OrderProduct> orderProducts
    ) {

        // member find
        MemberEntity memberEntity = memberJpaRepository.findById(memberId)
            .orElseThrow(() ->
                new EntityNotFoundException(
                    "[ERROR] 유효하지 않은 사용자 입니다. 조회 사용자 id: %s".formatted(memberId))
            );

        // receiver find
        ReceiverEntity receiverEntity = receiverJpaRepository.findById(receiverId)
            .orElseThrow(() ->
                new EntityNotFoundException(
                    "[ERROR] 유효하지 않은 배송지 입니다. 조회 배송지 id: %s".formatted(receiverId))
            );

        OrderEntity orderEntity = new OrderEntity(
            memberEntity,
            receiverEntity,
            type,
            UUID.randomUUID().toString(),
            totalPrice
        );

        OrderEntity saved = orderRepository.save(orderEntity);

        historyService.createHistory(saved.getId(), orderProducts);
        return OrderInfo.fromEntity(saved);
    }

    public OrderInfoWithHistory getOrder(Long orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId)
            .orElseThrow(() -> new EntityNotFoundException(
                "[ERROR] 유효하지 않은 주문 입니다. 조회 주문 id: %s".formatted(orderId))
            );

        List<HistoryInfo> history = historyService.getHistory(orderId);
        return OrderInfoWithHistory.fromEntity(orderEntity, history);
    }

    public List<OrderInfo> getOrders(){
        return orderRepository.findAll().stream().map(OrderInfo::fromEntity).toList();
    }

    public OrderInfo completeOrder(Long orderId){
        OrderEntity orderEntity = orderRepository.findById(orderId)
            .orElseThrow(() -> new EntityNotFoundException(
                "[ERROR] 유효하지 않은 주문 입니다. 조회 주문 id: %s".formatted(orderId))
            );

        OrderEntity completedOrder = orderEntity.complete();
        return OrderInfo.fromEntity(orderRepository.saveAndFlush(completedOrder));
    }

    public OrderInfo cancelOrder(Long orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId)
            .orElseThrow(() -> new EntityNotFoundException(
                "[ERROR] 유효하지 않은 주문 입니다. 조회 주문 id: %s".formatted(orderId))
            );

        OrderEntity completedOrder = orderEntity.cancel();
        return OrderInfo.fromEntity(orderRepository.saveAndFlush(completedOrder));
    }
}
