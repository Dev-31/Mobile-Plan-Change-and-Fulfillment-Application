package com.prodapt.mobileplan.util;

import com.prodapt.mobileplan.dto.response.PlanResponse;
import com.prodapt.mobileplan.entity.Plan;

public class PlanMapper {

    public static PlanResponse toResponse(Plan plan) {
        PlanResponse response = new PlanResponse();
        response.setId(plan.getId());
        response.setName(plan.getName());
        response.setType(plan.getType().name());
        response.setPrice(plan.getPrice());
        response.setDataInGb(plan.getDataInGb());
        response.setValidityInDays(plan.getValidityInDays());
        return response;
    }
}
