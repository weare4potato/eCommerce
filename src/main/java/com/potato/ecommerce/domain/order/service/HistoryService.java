package com.potato.ecommerce.domain.order.service;

import com.potato.ecommerce.domain.order.dto.HistoryInfo;
import com.potato.ecommerce.domain.order.dto.OrderProduct;
import com.potato.ecommerce.domain.order.entity.HistoryEntity;
import com.potato.ecommerce.domain.order.entity.OrderEntity;
import com.potato.ecommerce.domain.order.repository.HistoryRepository;
import com.potato.ecommerce.domain.order.repository.OrderRepository;
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
        OrderEntity orderEntity = orderRepository.findById(orderId)
            .orElseThrow(() -> new EntityNotFoundException(
                "[ERROR] 유효하지 않은 주문 입니다. 조회 주문 id: %s".formatted(orderId)));

        for (OrderProduct dto : orderProducts) {
            ProductEntity productEntity = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException(
                    "[ERROR] 유효하지 않은 상품 입니다. 조회 상품 id: %s".formatted(dto.getProductId())));

            productEntity.removeStock(dto.getQuantity());

            HistoryEntity historyEntity = HistoryEntity.builder()
                .order(orderEntity)
                .product(productEntity)
                .quantity(dto.getQuantity())
                .build();

            historyRepository.save(historyEntity);
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
        List<HistoryEntity> historyEntityList = historyRepository.findAllByOrderId(orderId);

        for (HistoryEntity historyEntity : historyEntityList) {

            historyEntity.cancel();
        }

        historyRepository.deleteAll(historyEntityList);
    }
}
