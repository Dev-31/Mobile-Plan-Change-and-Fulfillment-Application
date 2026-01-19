package com.prodapt.mobileplan.service;

import com.prodapt.mobileplan.entity.NotificationType;

public interface NotificationService {

    void notifyUser(Long userId, NotificationType type, String message);
}
