package com.prodapt.mobileplan.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.prodapt.mobileplan.dto.response.PlanResponse;
import com.prodapt.mobileplan.entity.PlanType;
import com.prodapt.mobileplan.repository.PlanRepository;
import com.prodapt.mobileplan.service.PlanService;
import com.prodapt.mobileplan.util.PlanMapper;

@Service
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;

    public PlanServiceImpl(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @Override
    public List<PlanResponse> getAllActivePlans() {
        return planRepository.findByActiveTrue()
                .stream()
                .map(PlanMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PlanResponse> getPlansByType(PlanType type) {
        return planRepository.findByTypeAndActiveTrue(type)
                .stream()
                .map(PlanMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PlanResponse> getPlansByPriceRange(double min, double max) {
        return planRepository.findByPriceBetweenAndActiveTrue(min, max)
                .stream()
                .map(PlanMapper::toResponse)
                .collect(Collectors.toList());
    }
}
