package com.prodapt.mobileplan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prodapt.mobileplan.entity.Plan;
import com.prodapt.mobileplan.entity.PlanType;

public interface PlanRepository extends JpaRepository<Plan, Long> {

    List<Plan> findByActiveTrue();
    List<Plan> findByPriceBetweenAndActiveTrue(double minPrice, double maxPrice);
    List<Plan> findByTypeAndActiveTrue(PlanType type);
}
