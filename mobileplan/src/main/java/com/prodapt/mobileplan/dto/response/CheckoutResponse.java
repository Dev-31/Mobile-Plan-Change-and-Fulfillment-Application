package com.prodapt.mobileplan.dto.response;

public class CheckoutResponse {

    private Long orderId;
    private double amount;
    private String status;

    public Long getOrderId() {
        return orderId;
    }

    public double getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
