package com.prodapt.mobileplan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prodapt.mobileplan.entity.Promotion;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    Optional<Promotion> findByCodeAndActiveTrue(String code);
}
