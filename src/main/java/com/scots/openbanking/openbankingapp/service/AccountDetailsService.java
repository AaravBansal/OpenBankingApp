package com.scots.openbanking.openbankingapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scots.openbanking.openbankingapp.dto.AccountDTO;
import com.scots.openbanking.openbankingapp.model.AccountsWrapper;
import com.scots.openbanking.openbankingapp.model.AccountsWrapper.Account;
import com.scots.openbanking.openbankingapp.model.BalancesWrapper;
import com.scots.openbanking.openbankingapp.model.BalancesWrapper.Balance;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AccountDetailsService {

    private final ObjectMapper objectMapper;

    public AccountDetailsService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<AccountDTO> getCombinedAccounts() throws Exception {
        // Load accounts.json
        InputStream accountsStream = getClass().getResourceAsStream("/mockdata/accounts.json");
        if (accountsStream == null) {
            throw new IllegalStateException("accounts.json file not found in resources");
        }
        AccountsWrapper accountsWrapper = objectMapper.readValue(accountsStream, AccountsWrapper.class);

        // Load balances.json
        InputStream balancesStream = getClass().getResourceAsStream("/mockdata/balances.json");
        if (balancesStream == null) {
            throw new IllegalStateException("balances.json file not found in resources");
        }
        BalancesWrapper balancesWrapper = objectMapper.readValue(balancesStream, BalancesWrapper.class);

        List<Account> accounts = getAccounts(accountsWrapper);

        List<Balance> balances = getBalances(balancesWrapper);

        // Map AccountId to Balance object for quick lookup
        Map<String, Balance> balanceMap = balances.stream()
                .collect(Collectors.toMap(Balance::getAccountId, b -> b));

        // Merge accounts and balances into AccountDTO
        return accounts.stream().map(acc -> {
            Balance balance = balanceMap.get(acc.getAccountId());

            // If balance exists in balances.json, override Balance and Currency from accounts.json
            String amount = (balance != null && balance.getAmount() != null) ? balance.getAmount().getAmount() : acc.getBalance();
            String currency = (balance != null && balance.getAmount() != null) ? balance.getAmount().getCurrency() : acc.getCurrency();

            AccountDTO dto = new AccountDTO();
            dto.setAccountId(acc.getAccountId());
            dto.setTitle(acc.getTitle());
            dto.setNickname(acc.getNickname());
            dto.setCurrency(currency);
            dto.setBalance(amount);
            dto.setAccountType(acc.getAccountType());
            dto.setAccountSubType(null); // no field in your JSON, so null or default
            dto.setBankId(acc.getBankName()); // or acc.getBankUrl() depending on use
            return dto;
        }).collect(Collectors.toList());
    }

    private static List<Balance> getBalances(BalancesWrapper balancesWrapper) {
        List<Balance> balances = balancesWrapper.getData().getBalance();
        if (balances == null) {
            throw new IllegalStateException("balances.json is missing the 'Data.Balance' structure or is malformed");
        }
        return balances;
    }

    private static List<Account> getAccounts(AccountsWrapper accountsWrapper) {
        List<Account> accounts = accountsWrapper.getData().getAccount();
        if (accounts == null) {
            throw new IllegalStateException("accounts.json is missing the 'Data.Account' structure or is malformed");
        }
        return accounts;
    }
}
