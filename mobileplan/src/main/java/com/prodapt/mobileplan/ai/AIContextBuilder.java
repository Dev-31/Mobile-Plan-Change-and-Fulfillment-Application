package com.prodapt.mobileplan.ai;

import java.util.List;

import com.prodapt.mobileplan.entity.Plan;
import com.prodapt.mobileplan.entity.Subscription;

public class AIContextBuilder {

    public static String buildUsageBasedContext(
            Subscription current,
            int dataUsedPercent,
            List<Plan> plans
    ) {
        return """
    User current plan: %s
    Data used: %d%%

    Available plans:
    %s

    Recommend ONE best plan and explain why briefly.
    """.formatted(
                current.getPlan().getName(),
                dataUsedPercent,
                plans.stream()
                        .map(p -> p.getName() + " - " + p.getDataInGb() + "GB/day")
                        .toList()
        );
    }


    private static String formatPlans(List<Plan> plans) {
        StringBuilder sb = new StringBuilder();
        for (Plan p : plans) {
            sb.append("- ")
                    .append(p.getName())
                    .append(" | ₹")
                    .append(p.getPrice())
                    .append(" | ")
                    .append(p.getDataInGb())
                    .append("GB/day | ")
                    .append(p.getValidityInDays())
                    .append(" days\n");
        }
        return sb.toString();
    }
}
