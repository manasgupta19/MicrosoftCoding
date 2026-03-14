package com.ai.chatbot.controller;

import com.ai.chatbot.model.*;
import com.ai.chatbot.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/ask")
    public ResponseEntity<ChatResponse> ask(@RequestParam String conversationId,
                                            @RequestBody ChatRequest request) {
        String output = chatService.handleCommand(conversationId, request.getPrompt());
        return ResponseEntity.ok(new ChatResponse(output, "SUCCESS"));
    }
}