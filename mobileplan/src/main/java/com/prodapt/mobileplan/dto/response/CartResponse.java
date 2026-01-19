package com.prodapt.mobileplan.dto.response;

import java.util.List;

public class CartResponse {

    private Long cartId;
    private List<String> planNames;

    // ✅ NEW FIELDS
    private int validityInDays;
    private double baseAmount;
    private double discount;
    private double payableAmount;

    // getters
    public Long getCartId() { return cartId; }
    public List<String> getPlanNames() { return planNames; }
    public int getValidityInDays() { return validityInDays; }
    public double getBaseAmount() { return baseAmount; }
    public double getDiscount() { return discount; }
    public double getPayableAmount() { return payableAmount; }

    // setters
    public void setCartId(Long cartId) { this.cartId = cartId; }
    public void setPlanNames(List<String> planNames) { this.planNames = planNames; }
    public void setValidityInDays(int validityInDays) {
        this.validityInDays = validityInDays;
    }
    public void setBaseAmount(double baseAmount) {
        this.baseAmount = baseAmount;
    }
    public void setDiscount(double discount) {
        this.discount = discount;
    }
    public void setPayableAmount(double payableAmount) {
        this.payableAmount = payableAmount;
    }

}
