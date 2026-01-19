package com.prodapt.mobileplan.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Component
public class GroqClient {

    private final WebClient webClient;
    private final String model;

    public GroqClient(
            @Value("${groq.api.key}") String apiKey,
            @Value("${groq.model}") String model) {

        this.model = model;

        this.webClient = WebClient.builder()
                .baseUrl("https://api.groq.com/openai/v1")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public Mono<String> ask(String prompt) {

        String body = """
        {
          "model": "%s",
          "messages": [
            { "role": "user", "content": "%s" }
          ]
        }
        """.formatted(model, prompt.replace("\"", "\\\""));

        return webClient.post()
                .uri("/chat/completions")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> extractAnswer(response));
    }

    private String extractAnswer(String response) {
        // simple JSON extraction (good enough for demo)
        int start = response.indexOf("\"content\":\"") + 11;
        int end = response.indexOf("\"", start);
        return response.substring(start, end);
    }
}
