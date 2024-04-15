package com.potato.ecommerce.domain.revenue.controller;

import com.potato.ecommerce.domain.revenue.dto.RevenueResponse;
import com.potato.ecommerce.domain.revenue.service.RevenueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Revenue API")
@RequiredArgsConstructor
public class RevenueController {

    private final RevenueService revenueService;

    @PostMapping("/api/v1/revenue")
    @Operation(summary = "사업자 번호 발급")
    public ResponseEntity<RevenueResponse> getBusinessNumber() {
        return ResponseEntity.status(HttpStatus.CREATED).body(revenueService.getBusinessNumber());
    }
}
