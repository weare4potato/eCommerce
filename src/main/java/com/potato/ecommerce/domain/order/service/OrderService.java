package com.potato.ecommerce.domain.order.service;

import com.potato.ecommerce.domain.member.entity.MemberEntity;
import com.potato.ecommerce.domain.member.repository.MemberJpaRepository;
import com.potato.ecommerce.domain.order.dto.HistoryInfo;
import com.potato.ecommerce.domain.order.dto.OrderInfo;
import com.potato.ecommerce.domain.order.dto.OrderInfoWithHistory;
import com.potato.ecommerce.domain.order.dto.OrderList;
import com.potato.ecommerce.domain.order.dto.OrderProduct;
import com.potato.ecommerce.domain.order.entity.OrderEntity;
import com.potato.ecommerce.domain.order.repository.order.OrderJpaRepository;
import com.potato.ecommerce.domain.order.repository.order.OrderQueryRepository;
import com.potato.ecommerce.domain.payment.vo.PaymentType;
import com.potato.ecommerce.domain.receiver.entity.ReceiverEntity;
import com.potato.ecommerce.domain.receiver.repository.ReceiverJpaRepository;
import com.potato.ecommerce.global.exception.ExceptionMessage;
import com.potato.ecommerce.global.util.RestPage;
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
    private final OrderJpaRepository orderJpaRepository;
    private final OrderQueryRepository orderQueryRepository;
    private final HistoryService historyService;

    public OrderInfo createOrder(
        Long memberId,
        Long receiverId,
        Long totalAmount,
        PaymentType type,
        List<OrderProduct> orderProducts
    ) {

        MemberEntity memberEntity = memberJpaRepository.findById(memberId)
            .orElseThrow(() ->
                new EntityNotFoundException(
                    ExceptionMessage.MEMBER_NOT_FOUND.toString())
            );

        ReceiverEntity receiverEntity = receiverJpaRepository.findById(receiverId)
            .orElseThrow(() ->
                new EntityNotFoundException(
                    ExceptionMessage.RECEIVER_NOT_FOUND.toString())
            );

        OrderEntity orderEntity = new OrderEntity(
            memberEntity,
            receiverEntity,
            type,
            UUID.randomUUID().toString(),
            totalAmount
        );

        OrderEntity saved = orderJpaRepository.save(orderEntity);

        historyService.createHistory(saved.getId(), orderProducts);

        return OrderInfo.fromEntity(saved);
    }

    @Transactional(readOnly = true)
    public OrderInfoWithHistory getOrder(Long orderId) {
        OrderEntity orderEntity = orderJpaRepository.findById(orderId)
            .orElseThrow(() -> new EntityNotFoundException(
                ExceptionMessage.ORDER_NOT_FOUND.toString())
            );

        List<HistoryInfo> history = historyService.getHistory(orderId);
        return OrderInfoWithHistory.fromEntity(orderEntity, history);
    }

    public RestPage<OrderList> getOrders(
        String subject,
        int page,
        int size
    ) {
        return orderQueryRepository.getOrders(subject, page, size);
    }


    public OrderInfo completeOrder(Long orderId) {
        OrderEntity orderEntity = orderJpaRepository.findById(orderId)
            .orElseThrow(() -> new EntityNotFoundException(
                ExceptionMessage.ORDER_NOT_FOUND.toString())
            );

        OrderEntity completedOrder = orderEntity.complete();
        return OrderInfo.fromEntity(orderJpaRepository.saveAndFlush(completedOrder));
    }

    public OrderInfo cancelOrder(Long orderId) {
        OrderEntity orderEntity = orderJpaRepository.findById(orderId)
            .orElseThrow(() -> new EntityNotFoundException(
                ExceptionMessage.ORDER_NOT_FOUND.toString())
            );

        OrderEntity completedOrder = orderEntity.cancel();

        historyService.deleteHistory(orderId);

        return OrderInfo.fromEntity(orderJpaRepository.saveAndFlush(completedOrder));
    }
}

