package com.prodapt.mobileplan.service.impl;

import org.springframework.stereotype.Service;

import com.prodapt.mobileplan.repository.PromotionRepository;
import com.prodapt.mobileplan.service.PromotionService;

@Service
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;

    public PromotionServiceImpl(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    @Override
    public double applyPromotion(String promoCode, double amount) {

        if (promoCode == null || promoCode.isBlank()) {
            return amount;
        }

        var promo = promotionRepository
                .findByCodeAndActiveTrue(promoCode)
                .orElseThrow(() -> new RuntimeException("Invalid promo code"));

        if (amount < promo.getMinAmount()) {
            throw new RuntimeException("Promo not applicable");
        }

        double discount = amount * (promo.getDiscountPercent() / 100);
        return amount - discount;
    }
}
