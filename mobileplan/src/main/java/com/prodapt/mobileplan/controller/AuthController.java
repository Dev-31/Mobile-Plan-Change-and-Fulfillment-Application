package com.prodapt.mobileplan.controller;

import com.prodapt.mobileplan.dto.response.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.prodapt.mobileplan.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestParam String mobile,
            @RequestParam String email,
            @RequestParam String password) {

        authService.register(mobile, email, password);
        return ResponseEntity.ok("Registration initiated. OTP sent to email.");
    }

    @PostMapping("/verify-register")
    public ResponseEntity<String> verifyRegister(
            @RequestParam String mobile,
            @RequestParam String otp) {

        authService.verifyRegistrationOtp(mobile, otp);
        return ResponseEntity.ok("Account verified successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestParam String mobile,
            @RequestParam String password) {

        authService.login(mobile, password);
        return ResponseEntity.ok("OTP sent for login.");
    }

    @PostMapping("/verify-login")
    public ResponseEntity<AuthResponse> verifyLogin(
            @RequestParam String mobile,
            @RequestParam String otp) {

        return ResponseEntity.ok(authService.verifyLoginOtp(mobile, otp));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String mobile) {
        authService.forgotPassword(mobile);
        return ResponseEntity.ok("OTP sent for password reset.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestParam String mobile,
            @RequestParam String otp,
            @RequestParam String newPassword) {

        authService.resetPassword(mobile, otp, newPassword);
        return ResponseEntity.ok("Password reset successful.");
    }
}
