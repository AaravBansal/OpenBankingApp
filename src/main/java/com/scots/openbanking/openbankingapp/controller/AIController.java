package com.scots.openbanking.openbankingapp.controller;

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


    @GetMapping("/test")
    public String testAI() throws Exception {

        return aiService.askFinancialQuestion(
                "What accounts do I have?"
        );
    }
}