package com.prodapt.mobileplan.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GroqResponseParser {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String extractAnswer(String json) {
        try {
            JsonNode root = mapper.readTree(json);
            return root
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();
        } catch (Exception e) {
            return "Sorry, I couldn't generate a response.";
        }
    }
}
