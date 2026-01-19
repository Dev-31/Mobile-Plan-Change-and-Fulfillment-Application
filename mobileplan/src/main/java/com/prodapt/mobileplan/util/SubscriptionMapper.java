package com.prodapt.mobileplan.util;

import java.time.LocalDate;

import com.prodapt.mobileplan.dto.response.SubscriptionResponse;
import com.prodapt.mobileplan.entity.Subscription;

public class SubscriptionMapper {

    public static SubscriptionResponse toResponse(Subscription subscription) {

        SubscriptionResponse response = new SubscriptionResponse();
        response.setSubscriptionId(subscription.getId());
        response.setPlanName(subscription.getPlan().getName());
        response.setPlanId(subscription.getPlan().getId());
        response.setPrice(subscription.getPlan().getPrice());
        response.setStartDate(subscription.getStartDate());
        response.setEndDate(subscription.getEndDate());
        response.setStatus(subscription.getStatus().name());

        // 🔔 Expiry alert logic
        boolean expiringSoon =
                subscription.getEndDate() != null &&
                        subscription.getEndDate().isBefore(LocalDate.now().plusDays(3));

        response.setExpiringSoon(expiringSoon);

        // 📊 Mock usage (for now)
        response.setDataUsedPercent(75);

        return response;
    }
}
