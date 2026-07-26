package com.scots.openbanking.openbankingapp.service;

import com.scots.openbanking.openbankingapp.dto.TransactionDTO;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FinancialInsightsService {

    private final TransactionCacheService transactionCacheService;


    public FinancialInsightsService(
            TransactionCacheService transactionCacheService
    ) {
        this.transactionCacheService = transactionCacheService;
    }



    public String generateInsights() {

        List<TransactionDTO> transactions =
                transactionCacheService.getTransactions();



        double income = transactions.stream()

                .filter(t ->
                        t.getCreditDebitIndicator()
                                .equalsIgnoreCase("CREDIT")
                )

                .mapToDouble(TransactionDTO::getAmount)

                .sum();



        double spending = transactions.stream()

                .filter(t ->
                        t.getCreditDebitIndicator()
                                .equalsIgnoreCase("DEBIT")
                )

                .mapToDouble(TransactionDTO::getAmount)

                .sum();



        Map<String, Double> categories =
                transactions.stream()

                        .filter(t ->
                                t.getCreditDebitIndicator()
                                        .equalsIgnoreCase("DEBIT")
                        )

                        .collect(Collectors.groupingBy(

                                TransactionDTO::getCategory,

                                Collectors.summingDouble(
                                        TransactionDTO::getAmount
                                )

                        ));



        StringBuilder result =
                new StringBuilder();



        result.append("""
                
                Financial Insights:

                Total income:
                %.2f NZD

                Total spending:
                %.2f NZD

                Savings:
                %.2f NZD


                Spending categories:

                """
                .formatted(
                        income,
                        spending,
                        income - spending
                ));



        categories.forEach(
                (category, value) ->
                        result.append(
                                "- %s: %.2f NZD\n"
                                        .formatted(
                                                category,
                                                value
                                        )
                        )
        );


        return result.toString();
    }
}