package com.potato.ecommerce.domain.cart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.potato.ecommerce.domain.cart.controller.dto.response.CartInfoResponseDto;
import com.potato.ecommerce.domain.cart.dto.CartInfo;
import com.potato.ecommerce.domain.cart.dto.CartRequest;
import com.potato.ecommerce.domain.cart.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
@Tag(name = "Cart API")
public class CartController {

    private final CartService cartService;
    private final ObjectMapper mapper;

    @PostMapping
    @Operation(summary = "장바구니 생성")
    public ResponseEntity<CartInfoResponseDto> addCart(
        @RequestBody @Valid CartRequest dto
    ) {
        CartInfo cartInfo = cartService.addCart(
            dto.getEmail(),
            dto.getProductId(),
            dto.getQuantity()
        );

        return ResponseEntity.status(HttpStatus.OK)
            .body(CartInfoResponseDto.from(cartInfo));
    }

    @PutMapping("/{cartId}")
    @Operation(summary = "장바구니 수정")
    public ResponseEntity<CartInfoResponseDto> updateCart(
        @PathVariable Long cartId,
        @RequestBody @Valid CartRequest dto
    ) {
        CartInfo cartInfo = cartService.updateCart(
            dto.getEmail(),
            cartId,
            dto.getQuantity()
        );

        return ResponseEntity.status(HttpStatus.OK)
            .body(CartInfoResponseDto.from(cartInfo));
    }

    @DeleteMapping("/{cartId}")
    @Operation(summary = "장바구니 삭제")
    public ResponseEntity<Void> deleteCart(
        HttpServletRequest httpServletRequest,
        @PathVariable Long cartId
    ) {
        String subject = getSubject(httpServletRequest);
        cartService.deleteCart(subject, cartId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    @Operation(summary = "장바구니 조회")
    public ResponseEntity<List<CartInfoResponseDto>> getCarts(
        HttpServletRequest httpServletRequest
    ) {
        String subject = getSubject(httpServletRequest);
        List<CartInfo> carts = cartService.getCarts(subject);
        ArrayList<CartInfo> list = new ArrayList<>();

        for (Object cart : carts) {
            list.add(mapper.convertValue(cart, CartInfo.class));
        }

        return ResponseEntity.status(HttpStatus.OK).body(
            list.stream().map(CartInfoResponseDto::from).toList()
        );
    }

    private String getSubject(HttpServletRequest request) {
        return (String) request.getAttribute("subject");
    }
}
