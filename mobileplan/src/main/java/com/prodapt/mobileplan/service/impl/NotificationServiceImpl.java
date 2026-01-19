package com.prodapt.mobileplan.service.impl;

import com.prodapt.mobileplan.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.prodapt.mobileplan.entity.NotificationType;
import com.prodapt.mobileplan.entity.User;
import com.prodapt.mobileplan.entity.UserProfile;
import com.prodapt.mobileplan.repository.UserProfileRepository;
import com.prodapt.mobileplan.repository.UserRepository;
import com.prodapt.mobileplan.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger log =
            LoggerFactory.getLogger(NotificationServiceImpl.class);

    private final UserProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public NotificationServiceImpl(
            UserProfileRepository profileRepository,
            UserRepository userRepository,
            EmailService emailService
    ) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @Override
    @Async
    public void notifyUser(Long userId, NotificationType type, String message) {

        try {
            UserProfile profile = profileRepository
                    .findByUserId(userId)
                    .orElseGet(() -> createDefaultProfile(userId));

            // ✅ EMAIL
            if (profile.isEmailEnabled() && profile.getEmail() != null) {
                emailService.sendEmail(
                        profile.getEmail(),
                        buildSubject(type),
                        message
                );
            }

            // ✅ SMS (future)
            if (profile.isSmsEnabled()) {
                log.info("[SMS MOCK] {} → {}", profile.getUser().getMobileNumber(), message);
            }

            // ✅ PUSH (future)
            if (profile.isPushEnabled()) {
                log.info("[PUSH MOCK] user {} → {}", userId, message);
            }

        } catch (Exception e) {
            // ❗ NEVER crash async thread
            log.error("Notification failed for user {} : {}", userId, e.getMessage());
        }
    }

    /* ===============================
       HELPERS
       =============================== */

    private UserProfile createDefaultProfile(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile profile = new UserProfile();
        profile.setUser(user);
        profile.setEmail(user.getMobileNumber() + "@mobileplan.com");
        profile.setEmailEnabled(true);
        profile.setSmsEnabled(false);
        profile.setPushEnabled(false);

        return profileRepository.save(profile);
    }

    private String buildSubject(NotificationType type) {
        return switch (type) {
            case PLAN_ACTIVATED -> "Your plan is now active";
            case PAYMENT_SUCCESS -> "Payment successful";
            case PAYMENT_FAILED -> "Payment failed";
            case PLAN_EXPIRING_SOON -> "Your plan is expiring soon";
            case PLAN_EXPIRY_REMINDER -> "Plan expiry reminder";
        };
    }
}
