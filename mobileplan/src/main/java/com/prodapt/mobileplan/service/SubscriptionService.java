package com.prodapt.mobileplan.service;

import java.util.Optional;

import com.prodapt.mobileplan.dto.request.PlanChangeRequest;
import com.prodapt.mobileplan.dto.response.SubscriptionResponse;
import com.prodapt.mobileplan.entity.Subscription;

public interface SubscriptionService {

    /**
     * Fetch the currently active subscription for a user.
     */
    Optional<SubscriptionResponse> getActiveSubscription(Long userId);
    Optional<Subscription> getActiveSubscriptionEntity(Long userId);
    /**
     * Change user's plan:
     * - Expire existing active subscription
     * - Create a new active subscription
     */
    SubscriptionResponse changePlan(PlanChangeRequest request);
}
