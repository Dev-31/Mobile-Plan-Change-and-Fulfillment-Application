package com.prodapt.mobileplan.dto.response;

import java.time.LocalDate;

public class SubscriptionResponse {

    private Long subscriptionId;

    // ✅ REQUIRED FOR RENEW FLOW
    private Long planId;

    private String planName;
    private double price;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;

    // 🔔 Alerts
    private boolean expiringSoon;

    // 📊 Mock usage
    private int dataUsedPercent;

    // getters & setters
    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isExpiringSoon() {
        return expiringSoon;
    }

    public void setExpiringSoon(boolean expiringSoon) {
        this.expiringSoon = expiringSoon;
    }

    public int getDataUsedPercent() {
        return dataUsedPercent;
    }

    public void setDataUsedPercent(int dataUsedPercent) {
        this.dataUsedPercent = dataUsedPercent;
    }
}
