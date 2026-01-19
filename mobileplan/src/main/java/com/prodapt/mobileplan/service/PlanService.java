package com.prodapt.mobileplan.service;

import java.util.List;

import com.prodapt.mobileplan.dto.response.PlanResponse;
import com.prodapt.mobileplan.entity.PlanType;

public interface PlanService {

    List<PlanResponse> getAllActivePlans();

    List<PlanResponse> getPlansByType(PlanType type);

    List<PlanResponse> getPlansByPriceRange(double min, double max);
}
