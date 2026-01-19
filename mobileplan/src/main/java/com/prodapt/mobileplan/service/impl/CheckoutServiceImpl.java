package com.prodapt.mobileplan.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prodapt.mobileplan.dto.request.CheckoutRequest;
import com.prodapt.mobileplan.dto.request.PlanChangeRequest;
import com.prodapt.mobileplan.dto.response.CheckoutResponse;
import com.prodapt.mobileplan.entity.Cart;
import com.prodapt.mobileplan.entity.Order;
import com.prodapt.mobileplan.entity.OrderStatus;
import com.prodapt.mobileplan.repository.CartRepository;
import com.prodapt.mobileplan.repository.OrderRepository;
import com.prodapt.mobileplan.service.CheckoutService;
import com.prodapt.mobileplan.service.PromotionService;
import com.prodapt.mobileplan.service.SubscriptionService;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final PromotionService promotionService;
    private final SubscriptionService subscriptionService;

    public CheckoutServiceImpl(
            CartRepository cartRepository,
            OrderRepository orderRepository,
            PromotionService promotionService,
            SubscriptionService subscriptionService) {

        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.promotionService = promotionService;
        this.subscriptionService = subscriptionService;
    }

    @Override
    @Transactional
    public CheckoutResponse checkout(CheckoutRequest request) {

        // 1️⃣ Fetch cart
        Cart cart = cartRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // 2️⃣ Get selected plan
        var item = cart.getItems().get(0);
        var plan = item.getPlan();

        // 3️⃣ Apply promotion
        double finalAmount = promotionService.applyPromotion(
                request.getPromoCode(),
                plan.getPrice()
        );

        // 4️⃣ Create PAID order (payment simulated)
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setPlan(plan);
        order.setAmount(finalAmount);
        order.setStatus(OrderStatus.PAID);

        order.setPaymentMode(
                request.getPaymentMode() != null
                        ? request.getPaymentMode()
                        : "CARD"
        );
        Order savedOrder = orderRepository.save(order);

        // 5️⃣ AUTO-ACTIVATE SUBSCRIPTION
        PlanChangeRequest planChangeRequest = new PlanChangeRequest();
        planChangeRequest.setUserId(cart.getUser().getId());
        planChangeRequest.setPlanId(plan.getId());

        subscriptionService.changePlan(planChangeRequest);

        // 6️⃣ Clear cart
        cartRepository.delete(cart);

        // 7️⃣ Prepare response
        CheckoutResponse response = new CheckoutResponse();
        response.setOrderId(savedOrder.getId());
        response.setAmount(savedOrder.getAmount());
        response.setStatus(savedOrder.getStatus().name());

        return response;
    }
}
