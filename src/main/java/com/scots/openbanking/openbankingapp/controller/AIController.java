package com.scots.openbanking.openbankingapp.controller;

import com.scots.openbanking.openbankingapp.dto.ChatRequest;
import com.scots.openbanking.openbankingapp.service.AIService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin
public class AIController {


    private final AIService aiService;


    public AIController(AIService aiService) {
        this.aiService = aiService;
    }


    @PostMapping("/chat")
    public String chat(@RequestBody ChatRequest request) throws Exception {

        return aiService.askFinancialQuestion(
                request.getMessage()
        );
    }
}