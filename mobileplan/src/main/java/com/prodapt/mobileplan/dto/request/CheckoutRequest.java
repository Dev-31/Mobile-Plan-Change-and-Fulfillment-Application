package com.prodapt.mobileplan.dto.request;
public class CheckoutRequest {

    private Long userId;
    private String promoCode;
    private String paymentMode; // ✅ ADD THIS

    public Long getUserId() { return userId; }
    public String getPromoCode() { return promoCode; }
    public String getPaymentMode() { return paymentMode; }

    public void setUserId(Long userId) { this.userId = userId; }
    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }
    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }
}

