package com.prodapt.mobileplan.scheduler;

import com.prodapt.mobileplan.entity.NotificationType;
import com.prodapt.mobileplan.entity.Subscription;
import com.prodapt.mobileplan.repository.SubscriptionRepository;
import com.prodapt.mobileplan.service.NotificationService;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class SubscriptionNotificationScheduler {

    private final SubscriptionRepository subscriptionRepository;
    private final NotificationService notificationService;

    public SubscriptionNotificationScheduler(
            SubscriptionRepository subscriptionRepository,
            NotificationService notificationService
    ) {
        this.subscriptionRepository = subscriptionRepository;
        this.notificationService = notificationService;
    }

    @Scheduled(cron = "0 0 9 * * ?") // every day at 9 AM
    public void notifyExpiringPlans() {

        List<Subscription> subs = subscriptionRepository.findAll();

        for (Subscription s : subs) {
            if (s.getEndDate() == null) continue;

            long daysLeft = ChronoUnit.DAYS.between(
                    LocalDate.now(),
                    s.getEndDate()
            );

            if (daysLeft == 5) {
                notificationService.notifyUser(
                        s.getUser().getId(),
                        NotificationType.PLAN_EXPIRING_SOON,
                        "Your plan will expire in 5 days. Please renew."
                );
            }
        }
    }
}
