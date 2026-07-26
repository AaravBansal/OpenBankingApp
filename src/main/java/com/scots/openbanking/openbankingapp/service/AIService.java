package com.scots.openbanking.openbankingapp.service;

import org.springframework.stereotype.Service;


@Service
public class AIService {


    private final FinancialContextService financialContextService;

    private final OllamaService ollamaService;



    public AIService(
            FinancialContextService financialContextService,
            OllamaService ollamaService
    ) {

        this.financialContextService =
                financialContextService;

        this.ollamaService =
                ollamaService;

    }



    public String askFinancialQuestion(String question)
            throws Exception {


        long start =
                System.currentTimeMillis();


        System.out.println(
                "AI request started"
        );



        String financialContext =
                financialContextService.buildContext();



        System.out.println(
                "Context built in "
                        +
                        (System.currentTimeMillis() - start)
                        +
                        "ms"
        );



        String prompt = """

        You are LNP AI, a personal financial assistant.

        Rules:
        - Only use the provided financial information.
        - Never make up values.
        - If information is unavailable say so.
        - Analyse accounts, transactions, investments and loans.
        - Give practical recommendations.
        - Use bullet points.


        COMPLETE FINANCIAL INFORMATION:

        %s


        USER QUESTION:

        %s


        RESPONSE:

        """
                .formatted(
                        financialContext,
                        question
                );



        String response =
                ollamaService.askAI(prompt);



        System.out.println(
                "Total AI time: "
                        +
                        (System.currentTimeMillis() - start)
                        +
                        "ms"
        );



        return response;

    }

}