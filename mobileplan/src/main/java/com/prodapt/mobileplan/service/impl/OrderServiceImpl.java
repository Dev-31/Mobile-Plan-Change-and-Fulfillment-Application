package com.prodapt.mobileplan.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prodapt.mobileplan.dto.request.CreateOrderRequest;
import com.prodapt.mobileplan.dto.request.PlanChangeRequest;
import com.prodapt.mobileplan.dto.response.OrderResponse;
import com.prodapt.mobileplan.entity.NotificationType;
import com.prodapt.mobileplan.entity.Order;
import com.prodapt.mobileplan.entity.OrderStatus;
import com.prodapt.mobileplan.entity.PaymentResult;
import com.prodapt.mobileplan.repository.OrderRepository;
import com.prodapt.mobileplan.repository.PlanRepository;
import com.prodapt.mobileplan.repository.UserRepository;
import com.prodapt.mobileplan.service.NotificationService;
import com.prodapt.mobileplan.service.OrderService;
import com.prodapt.mobileplan.service.PaymentService;
import com.prodapt.mobileplan.service.SubscriptionService;
import com.prodapt.mobileplan.util.OrderMapper;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private final PaymentService paymentService;
    private final SubscriptionService subscriptionService;
    private final NotificationService notificationService;

    public OrderServiceImpl(OrderRepository orderRepository,
                            UserRepository userRepository,
                            PlanRepository planRepository,
                            PaymentService paymentService,
                            SubscriptionService subscriptionService,
                            NotificationService notificationService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.planRepository = planRepository;
        this.paymentService = paymentService;
        this.subscriptionService = subscriptionService;
        this.notificationService = notificationService;
    }

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {

        var user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        var plan = planRepository.findById(request.getPlanId())
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        Order order = new Order();
        order.setUser(user);
        order.setPlan(plan);
        order.setAmount(plan.getPrice());
        order.setStatus(OrderStatus.CREATED);

        return OrderMapper.toResponse(orderRepository.save(order));
    }

    @Override
    public List<OrderResponse> getOrdersByUser(Long userId) {

        return orderRepository.findByUserId(userId)
                .stream()
                .map(order -> {
                    OrderResponse res = new OrderResponse();
                    res.setOrderId(order.getId());
                    res.setPlanName(order.getPlan().getName());
                    res.setAmount(order.getAmount());
                    res.setStatus(order.getStatus().name());
                    res.setCreatedAt(order.getCreatedAt());
                    res.setPaymentMode(order.getPaymentMode());
                    return res;
                })
                .toList();
    }



    @Override
    @Transactional
    public OrderResponse processPayment(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new RuntimeException("Order already processed");
        }

        PaymentResult result =
                paymentService.processPayment(order.getAmount());

        if (result == PaymentResult.SUCCESS) {

            order.setStatus(OrderStatus.PAID);
            orderRepository.save(order);

            // Notify payment success
            notificationService.notifyUser(
                    order.getUser().getId(),
                    NotificationType.PAYMENT_SUCCESS,
                    "Payment successful for plan " + order.getPlan().getName()
            );

            // Trigger plan activation
            PlanChangeRequest planChangeRequest = new PlanChangeRequest();
            planChangeRequest.setUserId(order.getUser().getId());
            planChangeRequest.setPlanId(order.getPlan().getId());

            subscriptionService.changePlan(planChangeRequest);

        } else {

            order.setStatus(OrderStatus.FAILED);
            orderRepository.save(order);

            // Notify payment failure
            notificationService.notifyUser(
                    order.getUser().getId(),
                    NotificationType.PAYMENT_FAILED,
                    "Payment failed. Please retry."
            );
        }

        return OrderMapper.toResponse(order);
    }

    @Override
    public OrderResponse getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(OrderMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
