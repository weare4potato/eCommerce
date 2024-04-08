package com.potato.ecommerce.domain.order.service;

import com.potato.ecommerce.domain.order.dto.HistoryInfo;
import com.potato.ecommerce.domain.order.dto.OrderProduct;
import com.potato.ecommerce.domain.order.model.History;
import com.potato.ecommerce.domain.order.model.Order;
import com.potato.ecommerce.domain.order.repository.history.HistoryRepository;
import com.potato.ecommerce.domain.order.repository.order.OrderRepository;
import com.potato.ecommerce.domain.product.entity.ProductEntity;
import com.potato.ecommerce.domain.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;
    // TODO: ProductJpaRepository 로 변경 가능성이 있음. 예외시 확인 필요
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public void createHistory(
        Long orderId,
        List<OrderProduct> orderProducts
    ) {
        Order order = orderRepository.findById(orderId);

        // TODO: product 수정되면 수정
        for (OrderProduct dto : orderProducts) {
            ProductEntity productEntity = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException(
                    "[ERROR] 유효하지 않은 상품 입니다. 조회 상품 id: %s".formatted(dto.getProductId())));

            productEntity.removeStock(dto.getQuantity());

            History history = History.builder()
                .order(order)
                .product(productEntity)
                .quantity(dto.getQuantity())
                .build();

            historyRepository.save(history);
        }
    }

    @Transactional(readOnly = true)
    public List<HistoryInfo> getHistory(
        Long orderId
    ) {
        return historyRepository.findAllByOrderId(orderId).stream()
            .map(HistoryInfo::fromEntity)
            .toList();
    }

    public void deleteHistory(
        Long orderId
    ) {
        List<History> historyList = historyRepository.findAllByOrderId(orderId);

        for (History history : historyList) {

            // TODO: product 수정되면 수정
            history.cancel();

            historyRepository.delete(history);
        }
    }
}
