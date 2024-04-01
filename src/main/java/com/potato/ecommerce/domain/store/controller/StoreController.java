package com.potato.ecommerce.domain.store.controller;

import com.potato.ecommerce.domain.store.dto.LoginRequest;
import com.potato.ecommerce.domain.store.dto.StoreRequest;
import com.potato.ecommerce.domain.store.dto.StoreResponse;
import com.potato.ecommerce.domain.store.dto.UpdateStoreRequest;
import com.potato.ecommerce.domain.store.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shops")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/signup")
    @Tag(name = "Store API")
    @Operation(summary = "상점 등록", description = "상점 등록입니다.")
    public ResponseEntity<Void> signup(@Valid @RequestBody StoreRequest storeRequest) {
        storeService.signup(storeRequest);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    @Tag(name = "Store API")
    @Operation(summary = "상점 로그인", description = "상점 로그인입니다.")
    public ResponseEntity<Void> signin(@Valid @RequestBody LoginRequest loginRequest){
        String token = storeService.signin(loginRequest);
        ResponseCookie cookie = ResponseCookie
            .from("Authorization", token)
            .domain("localhost")
            .path("/")
            .httpOnly(true)
            .maxAge(Duration.ofMinutes(30L))
            .build();

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .build();
    }

    @GetMapping
    @Tag(name = "Store API")
    @Operation(summary = "판매자 상점 조회", description = "판매자 상점 조회입니다.")
    public ResponseEntity<StoreResponse> getStores(
        HttpServletRequest request
    ){
        String subject = getSubject(request);

        StoreResponse storeResponse = storeService.getStores(subject);

        return ResponseEntity.ok().body(storeResponse);
    }

    @PutMapping
    @Tag(name = "Store API")
    @Operation(summary = "상점 수정")
    public ResponseEntity<StoreResponse> updateStore(
        HttpServletRequest request,
        @RequestBody UpdateStoreRequest updateRequest
    ){
        String subject = getSubject(request);

        StoreResponse storeResponse = storeService.updateStore(subject, updateRequest);

        return ResponseEntity.ok().body(storeResponse);
    }

    private static String getSubject(HttpServletRequest request) {
        return (String) request.getAttribute("subject");
    }
}
