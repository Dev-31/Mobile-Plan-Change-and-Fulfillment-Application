package com.prodapt.mobileplan.service.impl;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prodapt.mobileplan.dto.request.PlanChangeRequest;
import com.prodapt.mobileplan.dto.response.SubscriptionResponse;
import com.prodapt.mobileplan.entity.NotificationType;
import com.prodapt.mobileplan.entity.Plan;
import com.prodapt.mobileplan.entity.Subscription;
import com.prodapt.mobileplan.entity.SubscriptionStatus;
import com.prodapt.mobileplan.repository.PlanRepository;
import com.prodapt.mobileplan.repository.SubscriptionRepository;
import com.prodapt.mobileplan.repository.UserRepository;
import com.prodapt.mobileplan.service.NotificationService;
import com.prodapt.mobileplan.service.SubscriptionService;
import com.prodapt.mobileplan.util.SubscriptionMapper;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private final NotificationService notificationService;

    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository,
                                   UserRepository userRepository,
                                   PlanRepository planRepository,
                                   NotificationService notificationService) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
        this.planRepository = planRepository;
        this.notificationService = notificationService;
    }

    @Override
    public Optional<Subscription> getActiveSubscriptionEntity(Long userId) {
        return subscriptionRepository
                .findByUserIdAndStatus(userId, SubscriptionStatus.ACTIVE);
    }


    @Override
    public Optional<SubscriptionResponse> getActiveSubscription(Long userId) {

        return subscriptionRepository
                .findByUserIdAndStatus(userId, SubscriptionStatus.ACTIVE)
                .map(sub -> {

                    // 🔔 Notify if expiring soon
                    if (sub.getEndDate() != null &&
                            sub.getEndDate().isBefore(LocalDate.now().plusDays(3))) {

                        notificationService.notifyUser(
                                userId,
                                NotificationType.PLAN_EXPIRING_SOON,
                                "Your plan will expire on " + sub.getEndDate()
                        );
                    }

                    return SubscriptionMapper.toResponse(sub);
                });
    }


    @Override
    @Transactional
    public SubscriptionResponse changePlan(PlanChangeRequest request) {

        var user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Plan newPlan = planRepository.findById(request.getPlanId())
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        // 1️⃣ Expire existing active subscription
        subscriptionRepository
                .findByUserIdAndStatus(user.getId(), SubscriptionStatus.ACTIVE)
                .ifPresent(existing -> {
                    existing.setStatus(SubscriptionStatus.EXPIRED);
                    existing.setEndDate(java.time.LocalDate.now());
                    subscriptionRepository.save(existing);
                });

        // 2️⃣ Determine validity (custom or default)
        int validityDays =
                request.getValidityInDays() != null
                        ? request.getValidityInDays()
                        : newPlan.getValidityInDays();

        // 3️⃣ Create new subscription
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setPlan(newPlan);
        subscription.setStatus(SubscriptionStatus.ACTIVE);
        subscription.setStartDate(java.time.LocalDate.now());
        subscription.setEndDate(java.time.LocalDate.now().plusDays(validityDays));

        Subscription saved = subscriptionRepository.save(subscription);

        // 4️⃣ Notify user
        notificationService.notifyUser(
                user.getId(),
                NotificationType.PLAN_ACTIVATED,
                "Your plan " + newPlan.getName() + " has been renewed."
        );

        return SubscriptionMapper.toResponse(saved);
    }
}
