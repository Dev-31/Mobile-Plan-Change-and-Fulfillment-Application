package com.prodapt.mobileplan.service.impl;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.prodapt.mobileplan.entity.Otp;
import com.prodapt.mobileplan.repository.OtpRepository;
import com.prodapt.mobileplan.service.EmailService;
import com.prodapt.mobileplan.service.OtpService;

@Service
public class OtpServiceImpl implements OtpService {

    private final OtpRepository otpRepository;
    private final EmailService emailService;

    public OtpServiceImpl(
            OtpRepository otpRepository,
            EmailService emailService) {

        this.otpRepository = otpRepository;
        this.emailService = emailService;
    }

    @Override
    public void sendOtp(String destination, String purpose) {

        String code = String.valueOf(100000 + new Random().nextInt(900000));

        Otp otp = new Otp();
        otp.setDestination(destination);
        otp.setPurpose(purpose);
        otp.setCode(code);
        otp.setExpiresAt(LocalDateTime.now().plusMinutes(5));

        otpRepository.save(otp);

        // 🔥 EMAIL MUST NOT BREAK FLOW
        try {
            emailService.sendEmail(
                    destination,
                    "OTP Verification",
                    """
Your OTP is: %s
Purpose: %s
Valid for 5 minutes.

If you did not request this OTP, please ignore this email.
""".formatted(code, purpose)
            );
        } catch (Exception e) {
            // log only, never throw
            System.err.println("OTP email failed: " + e.getMessage());
        }
    }

    @Override
    public boolean verifyOtp(String destination, String purpose, String code) {

        return otpRepository
                .findTopByDestinationAndPurposeOrderByExpiresAtDesc(destination, purpose)
                .filter(o -> !o.isExpired())
                .map(o -> o.getCode().equals(code))
                .orElse(false);
    }
}
