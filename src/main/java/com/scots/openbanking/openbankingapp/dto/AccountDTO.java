package com.scots.openbanking.openbankingapp.dto;

// Data transfer Object (DTO) for exposing account data to the frontend or API consumers
public class AccountDTO {

    // Unique identifier for the account
    private String accountId;
    // Title or label for the account
    private String title;
    // User's nickname for the account
    private String nickname;
    // Currency code (e.g., GBP, USD)
    private String currency;
    // Current balance as a string
    private String balance;
    // Type of account (e.g., savings, checking)
    private String accountType;
    // Subtype of account (if any)
    private String accountSubType;
    // Identifier for the bank
    private String bankId;

    // Getters and Setters

    public String getAccountId() {
        return accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBalance() {
        return balance;
    }
    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getAccountType() {
        return accountType;
    }
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountSubType() {
        return accountSubType;
    }
    public void setAccountSubType(String accountSubType) {
        this.accountSubType = accountSubType;
    }

    public String getBankId() {
        return bankId;
    }
    public void setBankId(String bankId) {
        this.bankId = bankId;
    }
}
