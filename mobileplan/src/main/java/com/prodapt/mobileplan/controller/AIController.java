package com.prodapt.mobileplan.controller;

import org.springframework.web.bind.annotation.*;

import com.prodapt.mobileplan.dto.request.AIRequest;
import com.prodapt.mobileplan.dto.response.AIResponse;
import com.prodapt.mobileplan.service.AIService;

@RestController
@RequestMapping("/ai")
public class AIController {

    private final AIService aiService;

    public AIController(AIService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/ask")
    public AIResponse ask(@RequestBody AIRequest request) throws Exception {
        String answer = aiService.ask(
                request.getUserId(),
                request.getQuestion()
        );
        return new AIResponse(answer);
    }
}
