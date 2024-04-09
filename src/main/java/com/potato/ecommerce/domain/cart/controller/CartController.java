package com.potato.ecommerce.domain.cart.controller;

import static com.potato.ecommerce.domain.cart.message.CartMessage.ADD_CART;
import static com.potato.ecommerce.domain.cart.message.CartMessage.CART_API;
import static com.potato.ecommerce.domain.cart.message.CartMessage.DELETE_CART;
import static com.potato.ecommerce.domain.cart.message.CartMessage.GET_CARTS;
import static com.potato.ecommerce.domain.cart.message.CartMessage.UPDATE_CART;

import com.potato.ecommerce.domain.cart.controller.dto.response.CartInfoResponseDto;
import com.potato.ecommerce.domain.cart.dto.CartRequest;
import com.potato.ecommerce.domain.cart.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
@Tag(name = CART_API)
public class CartController {

    private final CartService cartService;

    @PostMapping
    @Operation(summary = ADD_CART)
    public ResponseEntity<Void> addCart(
        @RequestBody @Valid CartRequest dto
    ) {
        cartService.addCart(
            dto.getMemberId(),
            dto.getProductId(),
            dto.getQuantity()
        );

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{cartId}")
    @Operation(summary = UPDATE_CART)
    public ResponseEntity<Void> updateCart(
        @PathVariable Long cartId,
        @RequestBody @Valid CartRequest dto
    ) {
        cartService.updateCart(
            dto.getMemberId(),
            cartId,
            dto.getQuantity()
        );

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{cartId}")
    @Operation(summary = DELETE_CART)
    public ResponseEntity<Void> deleteCart(
        HttpServletRequest httpServletRequest,
        @PathVariable Long cartId
    ) {
        String subject = getSubject(httpServletRequest);
        cartService.deleteCart(subject, cartId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    @Operation(summary = GET_CARTS)
    public ResponseEntity<List<CartInfoResponseDto>> getCarts(
        HttpServletRequest httpServletRequest
    ) {
        String subject = getSubject(httpServletRequest);

        List<CartInfoResponseDto> carts = cartService.getCarts(subject).stream()
            .map(CartInfoResponseDto::from)
            .toList();

        return ResponseEntity.status(HttpStatus.OK).body(carts);
    }

    private String getSubject(HttpServletRequest request) {
        return (String) request.getAttribute("subject");
    }
}
