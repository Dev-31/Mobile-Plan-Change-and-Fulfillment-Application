package com.prodapt.mobileplan.service.impl;

import java.time.LocalDate;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.prodapt.mobileplan.entity.NotificationType;
import com.prodapt.mobileplan.entity.Subscription;
import com.prodapt.mobileplan.repository.SubscriptionRepository;
import com.prodapt.mobileplan.service.NotificationService;

@Component
public class PlanExpiryScheduler {

    private final SubscriptionRepository subscriptionRepository;
    private final NotificationService notificationService;

    public PlanExpiryScheduler(SubscriptionRepository subscriptionRepository,
                               NotificationService notificationService) {
        this.subscriptionRepository = subscriptionRepository;
        this.notificationService = notificationService;
    }

    @Scheduled(cron = "0 0 10 * * ?") // daily at 10 AM
    public void sendExpiryReminders() {

        LocalDate todayPlus3 = LocalDate.now().plusDays(3);

        subscriptionRepository.findAll().stream()
                .filter(sub -> sub.getEndDate() != null)
                .filter(sub -> todayPlus3.equals(sub.getEndDate()))
                .forEach(sub ->
                        notificationService.notifyUser(
                                sub.getUser().getId(),
                                NotificationType.PLAN_EXPIRY_REMINDER,
                                "Your plan will expire in 3 days."
                        )
                );
    }
}
