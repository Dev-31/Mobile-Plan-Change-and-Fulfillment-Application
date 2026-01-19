package com.prodapt.mobileplan.dto.request;

public class CreateOrderRequest {

    private Long userId;
    private Long planId;

    public Long getUserId() {
        return userId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }
}
