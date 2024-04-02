package com.potato.ecommerce.domain.order.controller;


import com.potato.ecommerce.domain.order.controller.dto.request.CreateOrderRequestDTO;
import com.potato.ecommerce.domain.order.controller.dto.response.OrderInfoResponseDTO;
import com.potato.ecommerce.domain.order.controller.dto.response.OrderInfoWithHistoryResponseDTO;
import com.potato.ecommerce.domain.order.dto.OrderInfo;
import com.potato.ecommerce.domain.order.dto.OrderInfoWithHistory;
import com.potato.ecommerce.domain.order.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Order API", description = "Order API 입니다.")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderInfoResponseDTO> createOrder(
        @RequestBody CreateOrderRequestDTO dto
    ){
        OrderInfo order = orderService.createOrder(
            dto.getMemberId(),
            dto.getReceiverId(),
            dto.getType(),
            dto.getTotalPrice(),
            dto.getOrderProducts()
        );
        return new ResponseEntity<>(OrderInfoResponseDTO.from(order), HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderInfoWithHistoryResponseDTO> getOrder(
        @PathVariable("orderId") Long orderId
    ){
        OrderInfoWithHistory order = orderService.getOrder(orderId);
        return new ResponseEntity<>(OrderInfoWithHistoryResponseDTO.from(order), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<OrderInfoResponseDTO>> getOrders(){
        List<OrderInfoResponseDTO> orders = orderService.getOrders().stream()
            .map(OrderInfoResponseDTO::from)
            .toList();

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping("/{orderId}/complete")
    public ResponseEntity<OrderInfoResponseDTO> completeOrder(
        @PathVariable("orderId") Long orderId
    ){
        OrderInfo order = orderService.completeOrder(orderId);
        return new ResponseEntity<>(OrderInfoResponseDTO.from(order), HttpStatus.OK);
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<OrderInfoResponseDTO> cancelOrder(
        @PathVariable("orderId") Long orderId
    ){
        OrderInfo order = orderService.cancelOrder(orderId);
        return new ResponseEntity<>(OrderInfoResponseDTO.from(order), HttpStatus.OK);
    }
}
