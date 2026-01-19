package com.prodapt.mobileplan.controller;

import org.springframework.web.bind.annotation.*;

import com.prodapt.mobileplan.dto.request.CheckoutRequest;
import com.prodapt.mobileplan.dto.response.CheckoutResponse;
import com.prodapt.mobileplan.service.CheckoutService;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping
    public CheckoutResponse checkout(@RequestBody CheckoutRequest request) {
        return checkoutService.checkout(request);
    }
}
