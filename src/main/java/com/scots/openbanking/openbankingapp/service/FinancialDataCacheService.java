package com.scots.openbanking.openbankingapp.service;

import com.scots.openbanking.openbankingapp.dto.AccountDTO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FinancialDataCacheService {

    private final AccountDetailsService accountDetailsService;
    private List<AccountDTO> accounts;

    public FinancialDataCacheService(
            AccountDetailsService accountDetailsService
    ) {

        this.accountDetailsService = accountDetailsService;

        refresh();
    }

    public void refresh() {
        try {
            accounts =
                    accountDetailsService.getCombinedAccounts();

            System.out.println(
                    "Financial data loaded into cache"
            );

        } catch(Exception e) {

            throw new RuntimeException(
                    "Could not load financial cache",
                    e
            );
        }
    }

    public List<AccountDTO> getAccounts() {

        System.out.println(
                "Using financial cache"
        );

        return accounts;
    }
}