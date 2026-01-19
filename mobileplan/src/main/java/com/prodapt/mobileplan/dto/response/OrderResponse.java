package com.prodapt.mobileplan.dto.response;

import java.time.LocalDateTime;

public class OrderResponse {
    private String paymentMode;
    private Long orderId;
    private String planName;
    private double amount;
    private String status;
    private LocalDateTime createdAt;

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getPlanName() {
        return planName;
    }

    public double getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
