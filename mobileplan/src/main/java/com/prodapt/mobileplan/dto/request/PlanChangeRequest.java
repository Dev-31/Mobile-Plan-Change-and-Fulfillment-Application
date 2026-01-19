package com.prodapt.mobileplan.dto.request;

public class PlanChangeRequest {

    private Long userId;
    private Long planId;

    // ✅ NEW (optional for renewal/customization)
    private Integer validityInDays;

    public Long getUserId() {
        return userId;
    }

    public Long getPlanId() {
        return planId;
    }

    public Integer getValidityInDays() {
        return validityInDays;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public void setValidityInDays(Integer validityInDays) {
        this.validityInDays = validityInDays;
    }
}
