package com.potato.ecommerce.domain.revenue.controller;

import com.potato.ecommerce.domain.revenue.dto.ResponseRevenue;
import com.potato.ecommerce.domain.revenue.service.RevenueService;
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
    public ResponseEntity<ResponseRevenue> getBusinessNumber() {
        return new ResponseEntity<>(revenueService.getBusinessNumber(), HttpStatus.CREATED);
    }
}
