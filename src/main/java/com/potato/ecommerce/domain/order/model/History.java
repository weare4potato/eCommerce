package com.potato.ecommerce.domain.order.model;

import com.potato.ecommerce.domain.order.entity.HistoryEntity;
import com.potato.ecommerce.domain.product.entity.ProductEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class History {

    private final Long id;
    private final Order order;
    private final ProductEntity product;
    private final Integer quantity;

    // Todo : product 수정시 수정
    @Builder
    private History(Long id, Order order, ProductEntity product, Integer quantity) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }

    // Todo : product 수정시 수정
    public HistoryEntity toEntity() {
        return HistoryEntity.builder()
            .id(id)
            .order(order.toEntity())
            .product(product)
            .quantity(quantity)
            .build();
    }

    // Todo : product 수정시 수정
    public static History fromEntity(HistoryEntity historyEntity) {
        return History.builder()
            .id(historyEntity.getId())
            .order(Order.fromEntity(historyEntity.getOrder()))
            .product(historyEntity.getProduct())
            .quantity(historyEntity.getQuantity())
            .build();
    }

    // Todo : product 수정시 수정
    public void cancel() {
        product.addStock(this.quantity);
    }
}
