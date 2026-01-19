package com.prodapt.mobileplan.controller;

import org.springframework.web.bind.annotation.*;

import com.prodapt.mobileplan.dto.request.AddToCartRequest;
import com.prodapt.mobileplan.dto.response.CartResponse;
import com.prodapt.mobileplan.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public CartResponse addToCart(@RequestBody AddToCartRequest request) {
        return cartService.addToCart(request);
    }

    @GetMapping("/{userId}")
    public CartResponse getCart(@PathVariable Long userId) {
        return cartService.getCart(userId);
    }

    @DeleteMapping("/{userId}")
    public void clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
    }
}
