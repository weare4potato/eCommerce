package com.potato.ecommerce.domain.cart.controller;

import com.potato.ecommerce.domain.cart.controller.dto.response.CartInfoResponseDto;
import com.potato.ecommerce.domain.cart.dto.CartRequest;
import com.potato.ecommerce.domain.cart.service.CartService;
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
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<Void> addCart(
        @RequestBody @Valid CartRequest dto
    ) {
        cartService.addCart(
            dto.getMemberId(),
            dto.getProductId(),
            dto.getQuantity()
        );

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<Void> updateCart(
        @PathVariable Long cartId,
        @RequestBody @Valid CartRequest dto
    ) {
        cartService.updateCart(
            dto.getMemberId(),
            cartId,
            dto.getQuantity()
        );

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCart(
        @PathVariable Long cartId,
        @RequestBody @Valid CartRequest dto
    ) {
        cartService.deleteCart(
            dto.getMemberId(),
            cartId
        );

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CartInfoResponseDto>> getCarts(
        @RequestBody @Valid CartRequest dto
    ) {

        List<CartInfoResponseDto> carts = cartService.getCarts(dto.getMemberId()).stream()
            .map(CartInfoResponseDto::from)
            .toList();

        return new ResponseEntity<>(carts, HttpStatus.OK);
    }
}
