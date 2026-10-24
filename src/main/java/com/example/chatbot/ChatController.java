package com.example.chatbot;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .defaultSystem("You are a helpful, concise assistant. If you are unsure, say so.")
                .build();
    }

    @PostMapping
    public Map<String, String> chat(@Valid @RequestBody ChatRequest request) {
        String message = request.message().trim();
        if (message.length() > 8000) {
            throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE,
                    "Message must be 8,000 characters or fewer.");
        }

        String answer = chatClient.prompt()
                .user(message)
                .call()
                .content();

        return Map.of("answer", answer == null ? "" : answer);
    }

    public record ChatRequest(@NotBlank(message = "message must not be blank") String message) {}
}
