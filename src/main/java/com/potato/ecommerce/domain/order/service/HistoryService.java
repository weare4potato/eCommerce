package com.potato.ecommerce.domain.order.service;

import com.potato.ecommerce.domain.order.dto.HistoryInfo;
import com.potato.ecommerce.domain.order.dto.OrderProduct;
import com.potato.ecommerce.domain.order.entity.HistoryEntity;
import com.potato.ecommerce.domain.order.entity.OrderEntity;
import com.potato.ecommerce.domain.order.repository.history.HistoryJpaRepository;
import com.potato.ecommerce.domain.order.repository.order.OrderJpaRepository;
import com.potato.ecommerce.domain.product.dto.ProductSimpleResponse;
import com.potato.ecommerce.domain.product.entity.ProductEntity;
import com.potato.ecommerce.domain.product.repository.ProductRepository;
import com.potato.ecommerce.global.exception.ExceptionMessage;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryJpaRepository historyJpaRepository;
    // TODO: ProductJpaRepository 로 변경 가능성이 있음. 예외시 확인 필요
    private final ProductRepository productRepository;
    private final OrderJpaRepository orderJpaRepository;

    public void createHistory(
        Long orderId,
        List<OrderProduct> orderProducts
    ) {
        OrderEntity orderEntity = orderJpaRepository.findById(orderId)
            .orElseThrow(() -> new EntityNotFoundException(
                ExceptionMessage.ORDER_NOT_FOUND.toString()));

        // Todo : 한번에 리스트로 받아서 벌크로 저장하는 방식을 생각해 볼 것

//        List<HistoryEntity> entities = new ArrayList<>();

        for (OrderProduct dto : orderProducts) {
            ProductEntity productEntity = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException(
                    ExceptionMessage.PRODUCT_NOT_FOUND.toString()));

            productEntity.removeStock(dto.getQuantity());

            Long totalOrderPrice = productEntity.getTotalPrice(dto.getQuantity());

            HistoryEntity historyEntity = HistoryEntity.builder()
                .order(orderEntity)
                .product(productEntity)
                .quantity(dto.getQuantity())
                .orderPrice(totalOrderPrice)
                .build();

            historyJpaRepository.save(historyEntity);
//            entities.add(historyEntity);
        }
//        historyJpaRepository.saveAll(entities);
    }

    @Transactional(readOnly = true)
    public List<HistoryInfo> getHistory(
        Long orderId
    ) {
        return historyJpaRepository.findAllByOrderId(orderId).stream()
            .map(e -> HistoryInfo.fromEntity(e, ProductSimpleResponse.of(e.getProduct())))
            .toList();
    }

    public void deleteHistory(
        String orderNum
    ) {
        List<HistoryEntity> historyEntityList = historyJpaRepository.findAllByOrder_OrderNum(orderNum);

        for (HistoryEntity historyEntity : historyEntityList) {

            historyEntity.cancel();
        }

        historyJpaRepository.deleteAll(historyEntityList);
    }
}
