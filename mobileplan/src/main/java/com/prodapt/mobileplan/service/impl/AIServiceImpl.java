package com.prodapt.mobileplan.service.impl;

import com.prodapt.mobileplan.ai.AIContextBuilder;
import com.prodapt.mobileplan.ai.GroqHttpClient;
import com.prodapt.mobileplan.ai.GroqResponseParser;
import com.prodapt.mobileplan.entity.AIMemory;
import com.prodapt.mobileplan.entity.Plan;
import com.prodapt.mobileplan.entity.Subscription;
import com.prodapt.mobileplan.repository.AIMemoryRepository;
import com.prodapt.mobileplan.repository.PlanRepository;
import com.prodapt.mobileplan.service.AIService;
import com.prodapt.mobileplan.service.SubscriptionService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AIServiceImpl implements AIService {

    private final GroqHttpClient groqHttpClient;
    private final SubscriptionService subscriptionService;
    private final PlanRepository planRepository;
    private final AIMemoryRepository memoryRepository;

    @Value("${groq.model}")
    private String model;

    public AIServiceImpl(
            GroqHttpClient groqHttpClient,
            SubscriptionService subscriptionService,
            PlanRepository planRepository,
            AIMemoryRepository memoryRepository
    ) {
        this.groqHttpClient = groqHttpClient;
        this.subscriptionService = subscriptionService;
        this.planRepository = planRepository;
        this.memoryRepository = memoryRepository;
    }

    @Override
    public String ask(Long userId, String question) throws Exception {

        /* =======================
           1️⃣ LOAD MEMORY
           ======================= */
        List<AIMemory> memories =
                memoryRepository.findTop5ByUserIdOrderByCreatedAtDesc(userId);

        String memoryContext = memories.isEmpty()
                ? "No previous conversation."
                : memories.stream()
                .map(m -> """
User asked: %s
Assistant replied: %s
""".formatted(m.getQuestion(), m.getAnswer()))
                .collect(Collectors.joining("\n"));

        /* =======================
           2️⃣ RESOLVE INTENT (🔥 FIX)
           ======================= */
        String intent = resolveIntent(question, memories);

        /* =======================
           3️⃣ LOAD USER CONTEXT
           ======================= */
        Subscription subscription = subscriptionService
                .getActiveSubscriptionEntity(userId)
                .orElseThrow(() -> new RuntimeException("No active subscription"));

        int dataUsedPercent = 75; // dashboard value
        String context;

        /* =======================
           4️⃣ INTENT HANDLING
           ======================= */
        switch (intent) {

            case "EXPIRY" -> {
                long daysLeft = ChronoUnit.DAYS.between(
                        LocalDate.now(),
                        subscription.getEndDate()
                );

                context = """
Plan name: %s
Expiry date: %s
Days remaining: %d

If days <= 5, say the plan is expiring soon.
Else say the plan is not expiring soon.
""".formatted(
                        subscription.getPlan().getName(),
                        subscription.getEndDate(),
                        daysLeft
                );
            }

            case "BENEFITS" -> {
                context = """
Plan name: %s
Price: ₹%.0f
Daily data: %d GB
Validity: %d days
Plan type: %s

Explain benefits clearly in bullet points.
""".formatted(
                        subscription.getPlan().getName(),
                        subscription.getPlan().getPrice(),
                        subscription.getPlan().getDataInGb(),
                        subscription.getPlan().getValidityInDays(),
                        subscription.getPlan().getType()
                );
            }

            case "USAGE" -> {
                context = """
Plan name: %s
Data used: %d%%

If usage >= 80%%, warn the user.
Else say usage is under control.
""".formatted(
                        subscription.getPlan().getName(),
                        dataUsedPercent
                );
            }

            case "RENEW" -> {
                long daysLeft = ChronoUnit.DAYS.between(
                        LocalDate.now(),
                        subscription.getEndDate()
                );

                context = """
Plan name: %s
Days remaining: %d

If days <= 5, strongly suggest renewal.
Else say renewal can be done later.
""".formatted(
                        subscription.getPlan().getName(),
                        daysLeft
                );
            }

            case "RECOMMEND" -> {
                List<Plan> plans = planRepository.findByActiveTrue();

                context = AIContextBuilder.buildUsageBasedContext(
                        subscription,
                        dataUsedPercent,
                        plans
                );
            }

            default -> {
                context = "Answer politely using telecom domain knowledge.";
            }
        }

        /* =======================
           5️⃣ FINAL PROMPT (🔥 FIX)
           ======================= */
        String finalContext = """
Previous conversation:
%s

Current context:
%s

User question:
%s
""".formatted(
                memoryContext,
                context,
                question
        );

        String payload = """
{
  "model": "%s",
  "messages": [
    {
      "role": "system",
      "content": "You are a telecom assistant. Use previous conversation to understand follow-up questions. Do not repeat information unless asked."
    },
    {
      "role": "user",
      "content": "%s"
    }
  ]
}
""".formatted(
                model,
                escapeJson(finalContext)
        );

        /* =======================
           6️⃣ GROQ CALL
           ======================= */
        String rawResponse = groqHttpClient.askGroq(payload);
        String answer = GroqResponseParser.extractAnswer(rawResponse);

        /* =======================
           7️⃣ SAVE MEMORY
           ======================= */
        AIMemory memory = new AIMemory();
        memory.setUserId(userId);
        memory.setQuestion(question);
        memory.setAnswer(answer);
        memory.setIntent(intent);

        memoryRepository.save(memory);

        return answer;
    }

    /* =======================
       🔧 INTENT RESOLUTION
       ======================= */
    private String resolveIntent(String question, List<AIMemory> memories) {
        String q = question.toLowerCase().trim();

        if (q.contains("expire") || q.contains("expiry")) return "EXPIRY";
        if (q.contains("renew")) return "RENEW";
        if (q.contains("usage") || q.contains("data")) return "USAGE";
        if (q.contains("benefit") || q.contains("feature")) return "BENEFITS";
        if (q.contains("suggest") || q.contains("best plan")) return "RECOMMEND";

        // 🔥 FOLLOW-UP QUESTIONS (CRITICAL)
        if (q.length() <= 5 && !memories.isEmpty()) {
            return memories.get(0).getIntent();
        }

        return "GENERAL";
    }

    /* =======================
       🔧 JSON ESCAPE
       ======================= */
    private String escapeJson(String text) {
        if (text == null) return "";
        return text
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "");
    }
}
