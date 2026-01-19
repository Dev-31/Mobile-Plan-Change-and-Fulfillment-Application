package com.prodapt.mobileplan.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.*;

import com.prodapt.mobileplan.dto.request.PlanChangeRequest;
import com.prodapt.mobileplan.dto.response.SubscriptionResponse;
import com.prodapt.mobileplan.service.SubscriptionService;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("/active/{userId}")
    public Optional<SubscriptionResponse> getActiveSubscription(
            @PathVariable Long userId) {
        return subscriptionService.getActiveSubscription(userId);
    }

    @PostMapping("/change")
    public SubscriptionResponse changePlan(
            @RequestBody PlanChangeRequest request) {
        return subscriptionService.changePlan(request);
    }
}
