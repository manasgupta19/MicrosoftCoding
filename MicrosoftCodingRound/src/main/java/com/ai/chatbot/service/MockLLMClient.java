package com.ai.chatbot.service;

import org.springframework.stereotype.Service;

@Service
public class MockLLMClient implements LLMClient {

    @Override
    public String query(String prompt) {
        // In a real scenario, you'd call an external API here (Gemini/ChatGPT).
        // For this design, we simulate the LLM's response.

        // 1. Internal logic: Force "One Word" via prompt instructions (conceptual)
        String rawOutput = simulateExternalApiCall(prompt);

        // 2. Sanitize: Ensure it's truly only one word
        String oneWord = rawOutput.trim().split("\\s+")[0].replaceAll("[^a-zA-Z]", "");

        // 3. Format as requested: "Response is: {one-word-answer}"
        return String.format("Response is: %s", oneWord);
    }

    private String simulateExternalApiCall(String prompt) {
        // Mocking a response for demonstration.
        if (prompt.toLowerCase().contains("capital of france")) return "Paris";
        if (prompt.toLowerCase().contains("color of sky")) return "Blue";
        return "Acknowledged";
    }
}