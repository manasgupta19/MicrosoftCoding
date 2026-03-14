package com.ai.chatbot.service;

public interface LLMClient {
    /**
     * Sends a query to the LLM and returns the raw response.
     */
    String query(String prompt);
}