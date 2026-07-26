package com.scots.openbanking.openbankingapp.service;

import com.scots.openbanking.openbankingapp.dto.AccountDTO;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class FinancialAnalyticsService {


    private final FinancialDataCacheService cacheService;
    private final NumberParserService numberParserService;


    public FinancialAnalyticsService(
            FinancialDataCacheService cacheService,
            NumberParserService numberParserService
    ) {
        this.cacheService = cacheService;
        this.numberParserService = numberParserService;
    }



    public String generateAnalytics() {


        List<AccountDTO> accounts =
                cacheService.getAccounts();



        double totalValue = accounts.stream()
                .mapToDouble(account ->
                        numberParserService.parseMoney(
                                account.getBalance()
                        )
                )
                .sum();



        AccountDTO largestAccount =
                accounts.stream()
                        .max(
                                Comparator.comparingDouble(
                                        account ->
                                                numberParserService.parseMoney(
                                                        account.getBalance()
                                                )
                                )
                        )
                        .orElse(null);



        Map<String, Long> bankBreakdown =
                accounts.stream()
                        .collect(
                                Collectors.groupingBy(
                                        AccountDTO::getBankId,
                                        Collectors.counting()
                                )
                        );



        StringBuilder analytics = new StringBuilder();


        analytics.append("Financial Analytics:\n\n");


        analytics.append("Number of accounts: ")
                .append(accounts.size())
                .append("\n");


        analytics.append("Total financial value: ")
                .append(String.format("%.2f", totalValue))
                .append(" NZD\n\n");



        if (largestAccount != null) {

            analytics.append("Largest account:\n")
                    .append(largestAccount.getBankId())
                    .append(" - ")
                    .append(largestAccount.getBalance())
                    .append(" ")
                    .append(largestAccount.getCurrency())
                    .append("\n\n");
        }



        analytics.append("Accounts by bank:\n");


        bankBreakdown.forEach((bank, count) -> {

            analytics.append("- ")
                    .append(bank)
                    .append(": ")
                    .append(count)
                    .append(" account(s)\n");

        });



        return analytics.toString();

    }

}