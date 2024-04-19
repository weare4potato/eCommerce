package com.potato.ecommerce.domain.order.controller.dto.request;

import com.potato.ecommerce.domain.order.dto.OrderProduct;
import com.potato.ecommerce.domain.payment.dto.PayType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequestDTO {

    private Long memberId;
    private Long receiverId;
    private PayType type;
    private Long totalAmount;
    private List<OrderProduct> orderProducts;
}
