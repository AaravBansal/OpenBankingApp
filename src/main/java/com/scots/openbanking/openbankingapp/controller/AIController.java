package com.scots.openbanking.openbankingapp.controller;

import com.scots.openbanking.openbankingapp.dto.ChatRequest;
import com.scots.openbanking.openbankingapp.service.AIService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "http://localhost:3000")
public class AIController {

    // Keep this limit aligned with FinancialChat so API clients and UI users
    // receive the same validation rule.
    private static final int MAX_MESSAGE_LENGTH = 500;
    private final AIService aiService;

    public AIController(AIService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/chat")
    public ResponseEntity<Map<String, String>> chat(@RequestBody ChatRequest request) throws Exception {
        String message = request.getMessage();

        // Reject invalid input before generating financial insights or calling
        // the model, both of which can be comparatively expensive operations.
        if (message == null || message.trim().isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", "Message cannot be empty."));
        }

        if (message.length() > MAX_MESSAGE_LENGTH) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "error",
                            "Message exceeds the " + MAX_MESSAGE_LENGTH + " character limit. Please shorten your question."
                    ));
        }

        // Log metadata only; user financial questions should not be written to
        // application logs.
        System.out.println("==============================");
        System.out.println("Chat endpoint hit. Message length: " + message.length());

        // The service enriches the question with current financial context and
        // delegates generation to the configured AI provider.
        String response = aiService.askFinancialQuestion(message);

        System.out.println("AI response generated.");
        System.out.println("==============================");

        return ResponseEntity.ok(Map.of("reply", response));
    }
}
