package com.ai.chatbot.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor // Required if you use certain frameworks, but good practice
public class Conversation {
    private String id;
    private String title;
    private List<ChatMessage> messages = new ArrayList<>();

    // Ensure this matches the (id, prompt) call in ChatService
    public Conversation(String id, String firstPrompt) {
        this.id = id;
        // Basic title logic: take the first word of the prompt
        String firstWord = (firstPrompt != null && firstPrompt.contains(" "))
                ? firstPrompt.trim().split("\\s+")[0]
                : firstPrompt;
        this.title = "Topic: " + firstWord;
    }
}