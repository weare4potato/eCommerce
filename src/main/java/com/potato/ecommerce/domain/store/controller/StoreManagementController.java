package com.potato.ecommerce.domain.store.controller;

import com.potato.ecommerce.domain.store.dto.OrderAcceptResponse;
import com.potato.ecommerce.domain.store.service.StoreManagementService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shops")
public class StoreManagementController {

    private final StoreManagementService storeManagementService;

    // todo 주문 접수 내역 목록
//    @GetMapping("/{shopsId}/sales")
//    public ResponseEntity<OrderAcceptHistoriesResponse> getOrderAcceptHistories(
//        HttpServletRequest httpServletRequest
//    ){
//
//    }

    // todo 주문 접수 내역 단건
//    @GetMapping("/{shopsId}/sales/{saleId}")
//    public ResponseEntity<OrderAcceptHistoryResponse> getOrderAcceptHistory(
//        HttpServletRequest httpServletRequest
//    ){
//
//    }

    // todo 매출 조회
//    @GetMapping("/{shopsId}/amount")
//    public ResponseEntity<AmountResponse> getAmount(
//        HttpServletRequest httpServletRequest
//    ){
//
//    }

    // todo 등록 상품 목록
//    @GetMapping("/shops/{shopsId}/products")
//    public ResponseEntity<ProductsResponse> getProducts(
//        HttpServletRequest httpServletRequest
//    ){
//
//    }
}
