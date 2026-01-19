package com.prodapt.mobileplan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prodapt.mobileplan.entity.Subscription;
import com.prodapt.mobileplan.entity.SubscriptionStatus;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByUserIdAndStatus(
            Long userId,
            SubscriptionStatus status
    );
}
