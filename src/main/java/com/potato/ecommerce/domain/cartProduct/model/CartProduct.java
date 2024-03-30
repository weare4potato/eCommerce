package com.potato.ecommerce.domain.cartProduct.model;

import com.potato.ecommerce.domain.product.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CartProduct {

    private Long id;
    private ProductEntity product;
    private int quantity;
    private int totalPrice;
}
