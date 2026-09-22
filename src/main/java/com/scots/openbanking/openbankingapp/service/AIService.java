package com.scots.openbanking.openbankingapp.service;

import org.springframework.stereotype.Service;

@Service
public class AIService {

    private final FinancialInsightsService financialInsightsService;
    private final OllamaService ollamaService;

    public AIService(
            FinancialInsightsService financialInsightsService,
            OllamaService ollamaService
    ) {
        this.financialInsightsService = financialInsightsService;
        this.ollamaService = ollamaService;
    }

    public String askFinancialQuestion(String question) throws Exception {
        // One timer covers insight generation and model inference, making it
        // easier to observe the total latency of an AI request.
        long start = System.currentTimeMillis();

        System.out.println("AI request started");

        // Generate fresh context for every request so the model answers from
        // the latest account and transaction data.
        String financialContext = financialInsightsService.generateInsights();

        System.out.println(
                "Financial context generated in "
                        + (System.currentTimeMillis() - start)
                        + " ms"
        );
        // The prompt explicitly bounds the model to the supplied financial
        // context, reducing the risk of fabricated balances or transactions.
        String prompt = """
                You are LNP AI, an intelligent personal financial assistant.

                You must ONLY use the financial information provided below.
                Do not invent transactions, balances or values.
                If the answer cannot be determined from the provided information,
                clearly state that.

                ============================
                FINANCIAL DATA
                ============================

                %s

                ============================
                USER QUESTION
                ============================

                %s

                ============================
                ANSWER
                ============================
                """
                .formatted(financialContext, question);

        // Ollama performs the final inference using the assembled prompt.
        String response = ollamaService.askAI(prompt);

        System.out.println(
                "Total AI time: "
                        + (System.currentTimeMillis() - start)
                        + " ms"
        );

        return response;
    }
}
