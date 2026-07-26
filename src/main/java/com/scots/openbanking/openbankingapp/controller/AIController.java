package com.scots.openbanking.openbankingapp.controller;

import com.scots.openbanking.openbankingapp.dto.ChatRequest;
import com.scots.openbanking.openbankingapp.service.AIService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "http://localhost:3000")
public class AIController {

    private final AIService aiService;


    public AIController(AIService aiService) {
        this.aiService = aiService;
    }


    @PostMapping("/chat")
    public Map<String, String> chat(@RequestBody ChatRequest request) throws Exception {

        System.out.println("==============================");
        System.out.println("🔥 CHAT ENDPOINT HIT 🔥");
        System.out.println("User message: " + request.getMessage());

        String response = aiService.askFinancialQuestion(
                request.getMessage()
        );

        System.out.println("AI RESPONSE:");
        System.out.println(response);

        System.out.println("==============================");


        return Map.of(
                "reply",
                response
        );
    }
}