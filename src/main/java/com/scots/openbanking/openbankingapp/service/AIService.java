package com.scots.openbanking.openbankingapp.service;

import com.scots.openbanking.openbankingapp.dto.AccountDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AIService {

    private final FinancialDataCacheService financialDataCacheService;
    private final OllamaService ollamaService;
    private final FinancialSummaryService financialSummaryService;
    private final FinancialAnalyticsService financialAnalyticsService;


    public AIService(
            FinancialDataCacheService financialDataCacheService,
            OllamaService ollamaService,
            FinancialSummaryService financialSummaryService,
            FinancialAnalyticsService financialAnalyticsService
    ) {
        this.financialDataCacheService = financialDataCacheService;
        this.ollamaService = ollamaService;
        this.financialSummaryService = financialSummaryService;
        this.financialAnalyticsService = financialAnalyticsService;
    }


    public String askFinancialQuestion(String question) throws Exception {


        long start = System.currentTimeMillis();

        System.out.println("AI request started");


        // Load cached accounts
        List<AccountDTO> accounts =
                financialDataCacheService.getAccounts();


        System.out.println(
                "Cache loaded: "
                        + (System.currentTimeMillis() - start)
                        + "ms"
        );



        StringBuilder financialContext = new StringBuilder();


        for (AccountDTO account : accounts) {

            financialContext.append(
                    """
                    
                    Account:
                    Name: %s
                    Bank: %s
                    Type: %s
                    Balance: %s %s
                    
                    """.formatted(
                            account.getNickname(),
                            account.getBankId(),
                            account.getAccountType(),
                            account.getBalance(),
                            account.getCurrency()
                    )
            );
        }



        String summary =
                financialSummaryService.generateSummary();


        System.out.println(
                "Summary generated: "
                        + (System.currentTimeMillis() - start)
                        + "ms"
        );



        String analytics =
                financialAnalyticsService.generateAnalytics();


        System.out.println(
                "Analytics generated: "
                        + (System.currentTimeMillis() - start)
                        + "ms"
        );



        String prompt = """

        You are LNP AI, a financial assistant.

        Rules:
        - Only use the provided financial information.
        - Never invent accounts or values.
        - Keep answers concise.
        - Use bullet points when helpful.
        - Explain financial concepts clearly.


        Financial Accounts:

        %s


        Financial Summary:

        %s


        Financial Analytics:

        %s


        User Question:

        %s


        Answer:

        """.formatted(
                financialContext,
                summary,
                analytics,
                question
        );



        String response =
                ollamaService.askAI(prompt);



        System.out.println(
                "Total AI request time: "
                        + (System.currentTimeMillis() - start)
                        + "ms"
        );


        return response;

    }
}