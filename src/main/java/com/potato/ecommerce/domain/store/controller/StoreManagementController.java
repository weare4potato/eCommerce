package com.potato.ecommerce.domain.store.controller;

import com.potato.ecommerce.domain.product.dto.ProductListResponse;
import com.potato.ecommerce.domain.store.service.StoreManagementService;
import com.potato.ecommerce.global.util.RestPage;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
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

//     todo 등록 상품 목록
    @GetMapping("/{shopsId}/products")
    public ResponseEntity<RestPage<ProductListResponse>> getProducts(
        HttpServletRequest httpServletRequest,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ){
        log.info("1");
        String subject = getSubject(httpServletRequest);

        return ResponseEntity.ok().body(storeManagementService.getProducts(subject, page, size));
    }

    private String getSubject(HttpServletRequest request) {
        return (String) request.getAttribute("subject");
    }
}
