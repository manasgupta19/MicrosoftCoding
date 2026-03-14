package com.ai.chatbot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {
    private String response; // Will be in "Response is: {answer}" format
    private String status;   // "SUCCESS" or "ERROR"
}