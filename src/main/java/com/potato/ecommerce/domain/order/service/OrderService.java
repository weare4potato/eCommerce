package com.potato.ecommerce.domain.order.service;

import com.potato.ecommerce.domain.member.dto.ResponseMember;
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
import com.potato.ecommerce.domain.payment.dto.PayType;
import com.potato.ecommerce.domain.receiver.dto.ReceiverForm;
import com.potato.ecommerce.domain.receiver.entity.ReceiverEntity;
import com.potato.ecommerce.domain.receiver.repository.ReceiverJpaRepository;
import com.potato.ecommerce.global.config.redis.lock.DistributedLock;
import com.potato.ecommerce.global.exception.ExceptionMessage;
import com.potato.ecommerce.global.util.RestPage;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Component
public class OrderService {

    private final MemberJpaRepository memberJpaRepository;
    private final ReceiverJpaRepository receiverJpaRepository;
    private final OrderJpaRepository orderJpaRepository;
    private final OrderQueryRepository orderQueryRepository;
    private final HistoryService historyService;
    private final RedisTemplate<String, RestPage<OrderList>> redisTemplate;

    @DistributedLock(key = "#orderProducts.![productId].toString()")
    public OrderInfo createOrder(
        Long memberId,
        Long receiverId,
        Long totalAmount,
        PayType type,
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

        return OrderInfo.fromEntity(
            saved,
            ResponseMember.fromEntity(memberEntity),
            ReceiverForm.fromEntity(receiverEntity)
        );
    }

    @Transactional(readOnly = true)
    public OrderInfoWithHistory getOrder(Long orderId) {
        OrderEntity orderEntity = orderJpaRepository.findById(orderId)
            .orElseThrow(() -> new EntityNotFoundException(
                ExceptionMessage.ORDER_NOT_FOUND.toString())
            );

        ResponseMember member = ResponseMember.fromEntity(orderEntity.getMember());
        ReceiverForm receiver = ReceiverForm.fromEntity(orderEntity.getReceiver());

        List<HistoryInfo> history = historyService.getHistory(orderId);
        return OrderInfoWithHistory.fromEntity(orderEntity, history, member, receiver);
    }

    @Transactional(readOnly = true)
    public RestPage<OrderList> getOrders(
        String subject,
        int page,
        int size
    ) {
        RestPage<OrderList> orderLists = redisTemplate.opsForValue().get(subject);
        if(orderLists != null){
            return orderLists;
        }
        RestPage<OrderList> orders = orderQueryRepository.getOrders(subject, page, size);
        redisTemplate.opsForValue().set(subject, orders, 3, TimeUnit.MINUTES);
        return orders;
    }

    @Transactional
    public OrderInfo completeOrder(String orderNum) {
        OrderEntity orderEntity = orderJpaRepository.findByOrderNum(orderNum)
            .orElseThrow(() -> new EntityNotFoundException(
                ExceptionMessage.ORDER_NOT_FOUND.toString())
            );

        OrderEntity result = orderJpaRepository.saveAndFlush(orderEntity.complete());
        ResponseMember member = ResponseMember.fromEntity(result.getMember());
        ReceiverForm receiver = ReceiverForm.fromEntity(result.getReceiver());

        return OrderInfo.fromEntity(
            result,
            member,
            receiver
        );
    }

    @Transactional
    public OrderInfo cancelOrder(String orderNum) {
        OrderEntity orderEntity = orderJpaRepository.findByOrderNum(orderNum)
            .orElseThrow(() -> new EntityNotFoundException(
                ExceptionMessage.ORDER_NOT_FOUND.toString())
            );

        OrderEntity result = orderJpaRepository.saveAndFlush(orderEntity.cancel());
        ResponseMember member = ResponseMember.fromEntity(result.getMember());
        ReceiverForm receiver = ReceiverForm.fromEntity(result.getReceiver());

        historyService.deleteHistory(orderNum);

        return OrderInfo.fromEntity(
            result,
            member,
            receiver
        );
    }
}

