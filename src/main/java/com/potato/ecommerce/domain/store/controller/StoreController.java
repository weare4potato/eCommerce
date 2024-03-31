package com.potato.ecommerce.domain.store.controller;

import com.potato.ecommerce.domain.store.dto.StoreRequest;
import com.potato.ecommerce.domain.store.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shops")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody StoreRequest storeRequest) {
        storeService.signup(storeRequest);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
