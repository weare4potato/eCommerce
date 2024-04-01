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
@RequiredArgsConstructor
public class RevenueController {

    private final RevenueService revenueService;

    @PostMapping("api/v1/revenue")
    @Tag(name = "Revenue API")
    @Operation(summary = "사업자 번호 발급", description = "사업자 번호 발급입니다.")
    public ResponseEntity<RevenueResponse> getBusinessNumber() {
        return new ResponseEntity<>(revenueService.getBusinessNumber(), HttpStatus.CREATED);
    }
}
