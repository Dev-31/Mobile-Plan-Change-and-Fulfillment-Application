package com.prodapt.mobileplan.service.impl;

import org.springframework.stereotype.Service;

import com.prodapt.mobileplan.entity.PaymentResult;
import com.prodapt.mobileplan.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Override
    public PaymentResult processPayment(double amount) {
        // Mock logic:
        // Any amount <= 500 succeeds
        return amount <= 500 ? PaymentResult.SUCCESS : PaymentResult.FAILED;
    }
}
