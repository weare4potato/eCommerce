package com.potato.ecommerce.domain.order.service;

import com.potato.ecommerce.domain.member.model.Member;
import com.potato.ecommerce.domain.member.repository.MemberRepository;
import com.potato.ecommerce.domain.order.dto.HistoryInfo;
import com.potato.ecommerce.domain.order.dto.OrderInfo;
import com.potato.ecommerce.domain.order.dto.OrderInfoWithHistory;
import com.potato.ecommerce.domain.order.dto.OrderList;
import com.potato.ecommerce.domain.order.dto.OrderProduct;
import com.potato.ecommerce.domain.order.model.Order;
import com.potato.ecommerce.domain.order.repository.order.OrderQueryRepository;
import com.potato.ecommerce.domain.order.repository.order.OrderRepository;
import com.potato.ecommerce.domain.payment.vo.PaymentType;
import com.potato.ecommerce.domain.receiver.model.Receiver;
import com.potato.ecommerce.domain.receiver.repository.ReceiverRepository;
import com.potato.ecommerce.global.util.RestPage;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final ReceiverRepository receiverRepository;
    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;
    private final HistoryService historyService;

    public OrderInfo createOrder(
        Long memberId,
        Long receiverId,
        PaymentType type,
        List<OrderProduct> orderProducts
    ) {

        Member member = memberRepository.findById(memberId);

        Receiver receiver = receiverRepository.findById(receiverId);

        Order order = Order.builder()
            .member(member)
            .receiver(receiver)
            .payment(type)
            .orderNum(UUID.randomUUID().toString())
            .build();

        Order saved = orderRepository.save(order);

        historyService.createHistory(saved.getId(), orderProducts);

        return OrderInfo.fromEntity(saved);
    }

    @Transactional(readOnly = true)
    public OrderInfoWithHistory getOrder(Long orderId) {

        Order order = orderRepository.findById(orderId);

        List<HistoryInfo> history = historyService.getHistory(orderId);
        return OrderInfoWithHistory.fromEntity(order, history);
    }

    public RestPage<OrderList> getOrders(
        String subject,
        int page,
        int size
    ) {
        return orderQueryRepository.getOrders(subject, page, size);
    }


    public OrderInfo completeOrder(Long orderId) {

        Order order = orderRepository.findById(orderId);

        Order completedOrder = order.complete();
        return OrderInfo.fromEntity(orderRepository.update(completedOrder));
    }

    public OrderInfo cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId);

        Order cancelOrder = order.cancel();

        historyService.deleteHistory(orderId);

        return OrderInfo.fromEntity(orderRepository.update(cancelOrder));
    }
}
