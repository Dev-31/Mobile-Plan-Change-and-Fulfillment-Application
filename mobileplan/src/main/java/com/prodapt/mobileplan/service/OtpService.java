package com.prodapt.mobileplan.service;

public interface OtpService {

    void sendOtp(String destination, String purpose);
    boolean verifyOtp(String destination, String purpose, String code);
}
