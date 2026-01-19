package com.prodapt.mobileplan.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.prodapt.mobileplan.dto.response.PlanResponse;
import com.prodapt.mobileplan.entity.PlanType;
import com.prodapt.mobileplan.service.PlanService;

@RestController
@RequestMapping("/plans")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping
    public List<PlanResponse> getAllPlans() {
        return planService.getAllActivePlans();
    }

    @GetMapping("/type/{type}")
    public List<PlanResponse> getPlansByType(@PathVariable PlanType type) {
        return planService.getPlansByType(type);
    }

    @GetMapping("/price")
    public List<PlanResponse> getPlansByPrice(
            @RequestParam double min,
            @RequestParam double max) {
        return planService.getPlansByPriceRange(min, max);
    }
}
