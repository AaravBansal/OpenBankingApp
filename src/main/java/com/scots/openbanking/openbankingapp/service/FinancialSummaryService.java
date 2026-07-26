package com.scots.openbanking.openbankingapp.service;

import com.scots.openbanking.openbankingapp.dto.AccountDTO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FinancialSummaryService {


    private final FinancialDataCacheService cacheService;
    private final NumberParserService numberParserService;


    public FinancialSummaryService(
            FinancialDataCacheService cacheService,
            NumberParserService numberParserService
    ) {
        this.cacheService = cacheService;
        this.numberParserService = numberParserService;
    }



    public String generateSummary() {


        List<AccountDTO> accounts =
                cacheService.getAccounts();



        double totalBalance = accounts.stream()
                .mapToDouble(account ->
                        numberParserService.parseMoney(
                                account.getBalance()
                        )
                )
                .sum();



        return """
                Financial Summary:

                Number of accounts:
                %d

                Combined balance:
                %.2f NZD

                """.formatted(
                accounts.size(),
                totalBalance
        );

    }

}