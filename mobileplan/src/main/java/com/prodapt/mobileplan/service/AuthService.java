package com.prodapt.mobileplan.service;

import com.prodapt.mobileplan.dto.response.AuthResponse;

public interface AuthService {

    void register(String mobile, String email, String password);

    void verifyRegistrationOtp(String mobile, String otp);

    void login(String mobile, String password);

    AuthResponse verifyLoginOtp(String mobile, String otp);

    void forgotPassword(String mobile);

    void resetPassword(String mobile, String otp, String newPassword);
}
