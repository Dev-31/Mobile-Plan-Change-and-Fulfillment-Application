package com.prodapt.mobileplan.service;

import com.prodapt.mobileplan.dto.request.CreateOrderRequest;
import com.prodapt.mobileplan.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(CreateOrderRequest request);

    OrderResponse processPayment(Long orderId);

    OrderResponse getOrder(Long orderId);

    List<OrderResponse> getOrdersByUser(Long userId);
}
