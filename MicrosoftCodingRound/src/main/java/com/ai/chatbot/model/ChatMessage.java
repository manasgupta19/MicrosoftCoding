package com.ai.chatbot.model;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ChatMessage {
    private String id;
    private String userQuery;
    private String botResponse;
    private LocalDateTime timestamp;

    public static ChatMessage create(String query, String response) {
        return ChatMessage.builder()
                .id(UUID.randomUUID().toString())
                .userQuery(query)
                .botResponse(response)
                .timestamp(LocalDateTime.now())
                .build();
    }
}