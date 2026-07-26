package com.scots.openbanking.openbankingapp.service;

import com.scots.openbanking.openbankingapp.dto.AccountDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AIService {

    private final AccountDetailsService accountDetailsService;
    private final OllamaService ollamaService;


    public AIService(
            AccountDetailsService accountDetailsService,
            OllamaService ollamaService
    ) {
        this.accountDetailsService = accountDetailsService;
        this.ollamaService = ollamaService;
    }


    public String askFinancialQuestion(String question) throws Exception {


        // Get account data from your existing JSON files
        List<AccountDTO> accounts =
                accountDetailsService.getCombinedAccounts();


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

        String prompt = """
                You are LNP AI, a helpful financial assistant.

                Answer the user's question using the financial information below.

                Financial information:

                %s


                User question:
                %s


                Keep your answer clear and concise.
                """.formatted(
                financialContext,
                question
        );


        return ollamaService.askAI(prompt);
    }
}