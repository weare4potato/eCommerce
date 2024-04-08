package com.potato.ecommerce.domain.store.controller;

import static com.potato.ecommerce.domain.store.message.StoreMessage.STORE_API;
import static com.potato.ecommerce.domain.store.message.StoreMessage.STORE_DELETE;
import static com.potato.ecommerce.domain.store.message.StoreMessage.STORE_DELETE_SUCCESS;
import static com.potato.ecommerce.domain.store.message.StoreMessage.STORE_INFO;
import static com.potato.ecommerce.domain.store.message.StoreMessage.STORE_PASSWORD_VALIDATION;
import static com.potato.ecommerce.domain.store.message.StoreMessage.STORE_PASSWORD_VALIDATION_SUCCESS;
import static com.potato.ecommerce.domain.store.message.StoreMessage.STORE_SIGN_IN;
import static com.potato.ecommerce.domain.store.message.StoreMessage.STORE_SIGN_UP;
import static com.potato.ecommerce.domain.store.message.StoreMessage.STORE_UPDATE;

import com.potato.ecommerce.domain.product.dto.ProductListResponse;
import com.potato.ecommerce.domain.store.dto.DeleteStoreRequest;
import com.potato.ecommerce.domain.store.dto.LoginRequest;
import com.potato.ecommerce.domain.store.dto.StoreRequest;
import com.potato.ecommerce.domain.store.dto.StoreResponse;
import com.potato.ecommerce.domain.store.dto.UpdateStoreRequest;
import com.potato.ecommerce.domain.store.dto.ValidatePasswordRequest;
import com.potato.ecommerce.domain.store.service.StoreService;
import com.potato.ecommerce.global.util.RestPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
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
@Tag(name = STORE_API)
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/signup")
    @Operation(summary = STORE_SIGN_UP)
    public ResponseEntity<Void> signup(@Valid @RequestBody StoreRequest storeRequest) {
        storeService.signup(storeRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/signin")
    @Operation(summary = STORE_SIGN_IN)
    public ResponseEntity<Void> signin(@Valid @RequestBody LoginRequest loginRequest) {
        String token = storeService.signin(loginRequest);
        ResponseCookie cookie = ResponseCookie
            .from("Authorization", token)
            .domain("localhost")
            .path("/")
            .httpOnly(true)
            .maxAge(Duration.ofMinutes(30L))
            .build();

        return ResponseEntity.status(HttpStatus.OK)
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .build();
    }

    @GetMapping
    @Operation(summary = STORE_INFO)
    public ResponseEntity<StoreResponse> getStores(
        HttpServletRequest request
    ) {
        String subject = getSubject(request);
        log.info(subject);

        StoreResponse storeResponse = storeService.getStores(subject);

        return ResponseEntity.status(HttpStatus.OK).body(storeResponse);
    }

    @PutMapping
    @Operation(summary = STORE_UPDATE)
    public ResponseEntity<StoreResponse> updateStore(
        HttpServletRequest request,
        @Valid @RequestBody UpdateStoreRequest updateRequest
    ) {
        String subject = getSubject(request);

        StoreResponse storeResponse = storeService.updateStore(subject, updateRequest);

        return ResponseEntity.status(HttpStatus.OK).body(storeResponse);
    }

    @PostMapping("/password")
    @Operation(summary = STORE_PASSWORD_VALIDATION)
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
    @Operation(summary = STORE_DELETE)
    public ResponseEntity<String> deleteStore(
        HttpServletRequest request,
        @Valid @RequestBody DeleteStoreRequest deleteStoreRequest
    ) {
        String subject = getSubject(request);

        storeService.deleteStore(subject, deleteStoreRequest);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(STORE_DELETE_SUCCESS);
    }

    @GetMapping("/{shopsId}/products")
    public ResponseEntity<RestPage<ProductListResponse>> getProducts(
        HttpServletRequest httpServletRequest,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        String subject = getSubject(httpServletRequest);

        return ResponseEntity.status(HttpStatus.OK).body(storeService.getProducts(subject, page, size));
    }

    private String getSubject(HttpServletRequest request) {
        return (String) request.getAttribute("subject");
    }
}
