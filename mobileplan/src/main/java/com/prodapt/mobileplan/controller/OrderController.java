package com.prodapt.mobileplan.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.prodapt.mobileplan.dto.request.CreateOrderRequest;
import com.prodapt.mobileplan.dto.response.OrderResponse;
import com.prodapt.mobileplan.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public OrderResponse createOrder(@RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
    }

    @PostMapping("/{orderId}/pay")
    public OrderResponse processPayment(@PathVariable Long orderId) {
        return orderService.processPayment(orderId);
    }

    @GetMapping("/{orderId}")
    public OrderResponse getOrder(@PathVariable Long orderId) {
        return orderService.getOrder(orderId);
    }

    @GetMapping("/user/{userId}")
    public List<OrderResponse> getOrdersByUser(@PathVariable Long userId) {
        return orderService.getOrdersByUser(userId);
    }
}
