package com.potato.ecommerce.domain.order.controller;


import com.potato.ecommerce.domain.order.controller.dto.request.CreateOrderRequestDTO;
import com.potato.ecommerce.domain.order.controller.dto.response.OrderInfoResponseDTO;
import com.potato.ecommerce.domain.order.controller.dto.response.OrderInfoWithHistoryResponseDTO;
import com.potato.ecommerce.domain.order.dto.OrderInfo;
import com.potato.ecommerce.domain.order.dto.OrderInfoWithHistory;
import com.potato.ecommerce.domain.order.dto.OrderList;
import com.potato.ecommerce.domain.order.service.OrderService;
import com.potato.ecommerce.global.util.RestPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Order API")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "주문 생성")
    public ResponseEntity<OrderInfoResponseDTO> createOrder(
        @RequestBody CreateOrderRequestDTO dto
    ) {
        OrderInfo order = orderService.createOrder(
            dto.getMemberId(),
            dto.getReceiverId(),
            dto.getTotalAmount(),
            dto.getType(),
            dto.getOrderProducts()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(OrderInfoResponseDTO.from(order));
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "주문 단건 조회")
    public ResponseEntity<OrderInfoWithHistoryResponseDTO> getOrder(
        @PathVariable Long orderId
    ) {
        OrderInfoWithHistory order = orderService.getOrder(orderId);

        return ResponseEntity.status(HttpStatus.OK)
            .body(OrderInfoWithHistoryResponseDTO.from(order));
    }

    @GetMapping
    @Operation(summary = "주문 목록 조회")
    public ResponseEntity<RestPage<OrderList>> getOrders(
        HttpServletRequest httpServletRequest,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        String subject = getSubject(httpServletRequest);

        return ResponseEntity.status(HttpStatus.OK)
            .body(orderService.getOrders(subject, page, size));
    }

    @PostMapping("/{orderNum}/complete")
    @Operation(summary = "주문 완료")
    public ResponseEntity<OrderInfoResponseDTO> completeOrder(
        @PathVariable String orderNum
    ) {
        OrderInfo order = orderService.completeOrder(orderNum);

        return ResponseEntity.status(HttpStatus.OK)
            .body(OrderInfoResponseDTO.from(order));
    }

    @PostMapping("/{orderNum}/cancel")
    @Operation(summary = "주문 취소")
    public ResponseEntity<OrderInfoResponseDTO> cancelOrder(
        @PathVariable String orderNum
    ) {
        OrderInfo order = orderService.cancelOrder(orderNum);

        return ResponseEntity.status(HttpStatus.OK)
            .body(OrderInfoResponseDTO.from(order));
    }

//    @GetMapping("/member")
//    public ResponseEntity<OrderInfoWithHistoryResponseDTO> getMemberOfOrder (
//        HttpServletRequest httpServletRequest
//    ) {
//        String subject = getSubject(httpServletRequest);
//
//        orderService.getMemberOfOrder(subject);
//    }

    private String getSubject(HttpServletRequest request) {
        return (String) request.getAttribute("subject");
    }
}
