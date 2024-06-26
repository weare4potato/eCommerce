package com.potato.ecommerce.domain.store.controller;

import static com.potato.ecommerce.domain.store.message.StoreMessage.STORE_DELETE_SUCCESS;
import static com.potato.ecommerce.domain.store.message.StoreMessage.STORE_PASSWORD_VALIDATION_SUCCESS;

import com.potato.ecommerce.domain.product.dto.ProductListResponse;
import com.potato.ecommerce.domain.store.dto.DeleteStoreRequest;
import com.potato.ecommerce.domain.store.dto.LoginRequest;
import com.potato.ecommerce.domain.store.dto.StoreRequest;
import com.potato.ecommerce.domain.store.dto.StoreResponse;
import com.potato.ecommerce.domain.store.dto.UpdateStoreRequest;
import com.potato.ecommerce.domain.store.dto.ValidatePasswordRequest;
import com.potato.ecommerce.domain.store.service.StoreService;
import com.potato.ecommerce.global.jwt.JwtUtil;
import com.potato.ecommerce.global.util.RestPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/shops")
@Tag(name = "Store API")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/signup")
    @Operation(summary = "상점 등록")
    public ResponseEntity<StoreResponse> signup(@Valid @RequestBody StoreRequest storeRequest) {
        StoreResponse storeResponse = storeService.signup(storeRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(storeResponse);
    }

    @PostMapping("/signin")
    @Operation(summary = "상점 로그인")
    public ResponseEntity<Void> signin(@Valid @RequestBody LoginRequest loginRequest) {
        String token = storeService.signin(loginRequest);

        return ResponseEntity.status(HttpStatus.OK)
            .header(JwtUtil.AUTHORIZATION_HEADER, token).build();
    }

    @GetMapping
    @Operation(summary = "상점 조회")
    public ResponseEntity<StoreResponse> getStores(
        HttpServletRequest request
    ) {
        String subject = getSubject(request);
        log.info(subject);

        StoreResponse storeResponse = storeService.getStore(subject);

        return ResponseEntity.status(HttpStatus.OK).body(storeResponse);
    }

    @PutMapping
    @Operation(summary = "상점 수정")
    public ResponseEntity<StoreResponse> updateStore(
        HttpServletRequest request,
        @Valid @RequestBody UpdateStoreRequest updateRequest
    ) {
        String subject = getSubject(request);

        StoreResponse storeResponse = storeService.updateStore(subject, updateRequest);

        return ResponseEntity.status(HttpStatus.OK).body(storeResponse);
    }

    @PostMapping("/password")
    @Operation(summary = "비밀번호 확인")
    public ResponseEntity<String> validatePassword(
        HttpServletRequest request,
        @Valid @RequestBody ValidatePasswordRequest validatePasswordRequest
    ) {
        String subject = getSubject(request);
        log.info(subject);

        storeService.validatePassword(subject, validatePasswordRequest);

        return ResponseEntity.status(HttpStatus.OK).body(STORE_PASSWORD_VALIDATION_SUCCESS);
    }

    @DeleteMapping
    @Operation(summary = "상점 탈퇴")
    public ResponseEntity<String> deleteStore(
        HttpServletRequest request,
        @Valid @RequestBody DeleteStoreRequest deleteStoreRequest
    ) {
        String subject = getSubject(request);

        storeService.deleteStore(subject, deleteStoreRequest);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(STORE_DELETE_SUCCESS);
    }

    @GetMapping("/products")
    @Operation(summary = "등록한 상품 조회")
    public ResponseEntity<RestPage<ProductListResponse>> getProducts(
        HttpServletRequest httpServletRequest,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        String subject = getSubject(httpServletRequest);

        return ResponseEntity.status(HttpStatus.OK)
            .body(storeService.getProducts(subject, page, size));
    }

    private String getSubject(HttpServletRequest request) {
        return (String) request.getAttribute("subject");
    }
}
