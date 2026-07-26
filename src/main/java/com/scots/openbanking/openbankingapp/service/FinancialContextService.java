package com.scots.openbanking.openbankingapp.service;


import org.springframework.stereotype.Service;



@Service
public class FinancialContextService {



    private final TransactionService transactionService;

    private final AccountService accountService;

    private final LoanService loanService;

    private final InvestmentService investmentService;

    private final FinancialInsightsService financialInsightsService;



    public FinancialContextService(

            TransactionService transactionService,

            AccountService accountService,

            LoanService loanService,

            InvestmentService investmentService,

            FinancialInsightsService financialInsightsService

    ){

        this.transactionService =
                transactionService;

        this.accountService =
                accountService;

        this.loanService =
                loanService;

        this.investmentService =
                investmentService;

        this.financialInsightsService =
                financialInsightsService;

    }




    public String buildContext(){


        StringBuilder builder =
                new StringBuilder();



        builder.append(
                "\n===== ACCOUNTS =====\n"
        );


        builder.append(
                accountService.getAccountsAsText()
        );



        builder.append(
                "\n===== TRANSACTIONS =====\n"
        );


        builder.append(
                transactionService.getTransactionsAsText()
        );



        builder.append(
                "\n===== LOANS =====\n"
        );


        builder.append(
                loanService.getLoansAsText()
        );



        builder.append(
                "\n===== INVESTMENTS =====\n"
        );


        builder.append(
                investmentService.getInvestmentsAsText()
        );



        builder.append(
                "\n===== CALCULATED INSIGHTS =====\n"
        );


        builder.append(
                financialInsightsService.generateInsights()
        );



        return builder.toString();

    }

}