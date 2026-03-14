package com.ai.chatbot.cli;

import com.ai.chatbot.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TerminalInterface implements CommandLineRunner {

    private final ChatService chatService;
    private String currentConversationId = UUID.randomUUID().toString();

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- AI Chatbot Terminal Started ---");
        System.out.println("Commands: /new, /list, /history, /exit");

        while (true) {
            System.out.print("\nUser [" + currentConversationId.substring(0, 8) + "]> ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("/exit")) {
                System.out.println("Goodbye!");
                System.exit(0);
            }

            // Route commands or queries through the existing logic
            String response = chatService.handleCommand(currentConversationId, input);

            // If user started a new chat, update the local pointer
            if (input.startsWith("/new") && response.contains("ID: ")) {
                currentConversationId = response.split("ID: ")[1];
            }

            System.out.println(response);
        }
    }
}