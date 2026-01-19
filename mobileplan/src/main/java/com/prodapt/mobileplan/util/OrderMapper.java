package com.prodapt.mobileplan.util;

import com.prodapt.mobileplan.dto.response.OrderResponse;
import com.prodapt.mobileplan.entity.Order;

public class OrderMapper {

    public static OrderResponse toResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getId());
        response.setPlanName(order.getPlan().getName());
        response.setAmount(order.getAmount());
        response.setStatus(order.getStatus().name());
        response.setCreatedAt(order.getCreatedAt());
        return response;
    }
}
