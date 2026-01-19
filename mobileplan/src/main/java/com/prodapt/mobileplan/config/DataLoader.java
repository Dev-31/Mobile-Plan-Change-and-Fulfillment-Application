package com.prodapt.mobileplan.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.prodapt.mobileplan.entity.Plan;
import com.prodapt.mobileplan.entity.PlanType;
import com.prodapt.mobileplan.repository.PlanRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadPlans(PlanRepository planRepository) {
        return args -> {

            if (planRepository.count() == 0) {

                Plan p1 = new Plan();
                p1.setName("Prepaid Smart 199");
                p1.setType(PlanType.PREPAID);
                p1.setPrice(199);
                p1.setDataInGb(2);
                p1.setValidityInDays(28);

                Plan p2 = new Plan();
                p2.setName("Prepaid Unlimited 299");
                p2.setType(PlanType.PREPAID);
                p2.setPrice(299);
                p2.setDataInGb(3);
                p2.setValidityInDays(28);

                Plan p3 = new Plan();
                p3.setName("Postpaid Plus 499");
                p3.setType(PlanType.POSTPAID);
                p3.setPrice(499);
                p3.setDataInGb(50);
                p3.setValidityInDays(30);

                planRepository.save(p1);
                planRepository.save(p2);
                planRepository.save(p3);
            }
        };
    }
}
