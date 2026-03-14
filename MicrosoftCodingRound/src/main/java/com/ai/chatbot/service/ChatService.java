package com.ai.chatbot.service;

import com.ai.chatbot.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final MockLLMClient llmClient;
    private final Map<String, Conversation> conversationMap = new ConcurrentHashMap<>();

    // Define the list of allowed commands
    private static final Set<String> ALLOWED_COMMANDS = Set.of("/new", "/list", "/history");

    public String handleCommand(String currentId, String input) {
        String trimmedInput = input.trim();

        // Validation Logic: If it starts with '/', it MUST be a valid command
        if (trimmedInput.startsWith("/")) {
            String command = trimmedInput.split("\\s+")[0].toLowerCase();

            if (!ALLOWED_COMMANDS.contains(command)) {
                return "Error: '" + command + "' is not a recognized command. " +
                        "Available commands: " + ALLOWED_COMMANDS;
            }

            // Route to specific command logic
            return executeValidCommand(currentId, command);
        }

        // If it doesn't start with '/', it's a regular query
        return processLlmQuery(currentId, trimmedInput);
    }

    private String executeValidCommand(String currentId, String command) {
        switch (command) {
            case "/new":
                String newId = UUID.randomUUID().toString();
                return "New chat started. Session ID: " + newId;

            case "/list":
                if (conversationMap.isEmpty()) return "No conversations found.";
                return conversationMap.values().stream()
                        .map(c -> "[" + c.getId().substring(0, 8) + "] " + c.getTitle())
                        .collect(Collectors.joining("\n"));

            case "/history":
                Conversation conv = conversationMap.get(currentId);
                if (conv == null || conv.getMessages().isEmpty()) return "No history for this session.";
                return conv.getMessages().stream()
                        .map(m -> "You: " + m.getUserQuery() + "\nBot: " + m.getBotResponse())
                        .collect(Collectors.joining("\n---\n"));

            default:
                return "Command logic not implemented.";
        }
    }

    private String processLlmQuery(String id, String query) {
        // Prevent empty strings from reaching the LLM
        if (query.isEmpty()) return "Please enter a question.";

        Conversation conv = conversationMap.computeIfAbsent(id, k -> new Conversation(id, query));
        String response = llmClient.query(query);
        conv.getMessages().add(ChatMessage.create(query, response));
        return response;
    }
}