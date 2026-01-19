package com.prodapt.mobileplan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prodapt.mobileplan.entity.Otp;

public interface OtpRepository extends JpaRepository<Otp, Long> {

    Optional<Otp> findTopByDestinationAndPurposeOrderByExpiresAtDesc(
            String destination, String purpose);
}
