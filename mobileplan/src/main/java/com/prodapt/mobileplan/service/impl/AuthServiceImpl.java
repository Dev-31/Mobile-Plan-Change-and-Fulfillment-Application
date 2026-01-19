package com.prodapt.mobileplan.service.impl;

import com.prodapt.mobileplan.dto.response.AuthResponse;
import com.prodapt.mobileplan.entity.User;
import com.prodapt.mobileplan.entity.UserAuth;
import com.prodapt.mobileplan.repository.UserAuthRepository;
import com.prodapt.mobileplan.repository.UserRepository;
import com.prodapt.mobileplan.service.AuthService;
import com.prodapt.mobileplan.service.JwtService;
import com.prodapt.mobileplan.service.OtpService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final UserAuthRepository userAuthRepository;
    private final UserRepository userRepository;
    private final OtpService otpService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthServiceImpl(
            JwtService jwtService,
            UserAuthRepository userAuthRepository,
            UserRepository userRepository,
            OtpService otpService) {

        this.jwtService = jwtService;
        this.userAuthRepository = userAuthRepository;
        this.userRepository = userRepository;
        this.otpService = otpService;
    }

    /* ================= REGISTER ================= */

    @Override
    public void register(String mobile, String email, String password) {

        // 🔎 Check existing user by email or mobile
        var existingByMobile = userAuthRepository.findByMobile(mobile);
        var existingByEmail = userAuthRepository.findByEmail(email);

        UserAuth auth;

        if (existingByMobile.isPresent() || existingByEmail.isPresent()) {

            auth = existingByMobile
                    .or(() -> existingByEmail)
                    .get();

            // 🚫 Already verified → block
            if (auth.isVerified()) {
                throw new RuntimeException("Account already exists. Please login.");
            }

            // 🔁 Not verified → resend OTP
            otpService.sendOtp(auth.getEmail(), "REGISTER");
            return;
        }

        // 🆕 New user
        auth = new UserAuth();
        auth.setMobile(mobile);
        auth.setEmail(email);
        auth.setPasswordHash(passwordEncoder.encode(password));
        auth.setVerified(false);

        userAuthRepository.save(auth);

        otpService.sendOtp(email, "REGISTER");
    }

    @Override
    public void verifyRegistrationOtp(String mobile, String otp) {

        UserAuth auth = userAuthRepository.findByMobile(mobile)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean valid = otpService.verifyOtp(
                auth.getEmail(),
                "REGISTER",
                otp
        );

        if (!valid) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        auth.setVerified(true);
        userAuthRepository.save(auth);
    }

    /* ================= LOGIN ================= */

    @Override
    public void login(String mobile, String password) {

        UserAuth auth = userAuthRepository.findByMobile(mobile)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!auth.isVerified()) {
            throw new RuntimeException("Account not verified");
        }

        if (!passwordEncoder.matches(password, auth.getPasswordHash())) {
            throw new RuntimeException("Invalid password");
        }

        // 🔥 SEND OTP TO EMAIL
        otpService.sendOtp(auth.getEmail(), "LOGIN");
    }


    @Override
    public AuthResponse verifyLoginOtp(String mobile, String otp) {

        UserAuth auth = userAuthRepository.findByMobile(mobile)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean valid = otpService.verifyOtp(
                auth.getEmail(),
                "LOGIN",
                otp
        );

        if (!valid) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        User user = userRepository.findByMobileNumber(mobile)
                .orElseGet(() -> {
                    User u = new User();
                    u.setMobileNumber(mobile);
                    return userRepository.save(u);
                });

        String token = jwtService.generateToken(mobile);
        return new AuthResponse(user.getId(), token);
    }


    /* ================= PASSWORD RESET ================= */

    @Override
    public void forgotPassword(String mobile) {

        UserAuth auth = userAuthRepository.findByMobile(mobile)
                .orElseThrow(() -> new RuntimeException("User not found"));

        otpService.sendOtp(auth.getEmail(), "RESET");
    }

    @Override
    public void resetPassword(String mobile, String otp, String newPassword) {

        UserAuth auth = userAuthRepository.findByMobile(mobile)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean valid = otpService.verifyOtp(
                auth.getEmail(),
                "RESET",
                otp
        );

        if (!valid) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        auth.setPasswordHash(passwordEncoder.encode(newPassword));
        userAuthRepository.save(auth);
    }

}
