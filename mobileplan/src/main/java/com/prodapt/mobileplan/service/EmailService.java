package com.prodapt.mobileplan.service;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}
