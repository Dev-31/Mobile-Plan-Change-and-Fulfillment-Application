package com.prodapt.mobileplan.service;

import com.prodapt.mobileplan.entity.PaymentResult;

public interface PaymentService {

    PaymentResult processPayment(double amount);
}
