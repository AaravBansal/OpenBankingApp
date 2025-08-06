package com.scots.openbanking.openbankingapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

// Wrapper class for account data, matching the expected JSON structure
public class AccountsWrapper {

    @JsonProperty("Data")
    private Data Data; // Top-level "Data" field

    public Data getData() {
        return Data;
    }

    public void setData(Data data) {
        this.Data = data;
    }

    // Nested static class representing the "Data" object
    public static class Data {

        @JsonProperty("Account")
        private List<Account> Account; // List of accounts

        public List<Account> getAccount() {
            return Account;
        }

        public void setAccount(List<Account> account) {
            this.Account = account;
        }
    }

    // Nested static class representing an individual account
    public static class Account {

        @JsonProperty("AccountId")
        private String accountId;

        @JsonProperty("Nickname")
        private String nickname;

        @JsonProperty("AccountType")
        private String accountType;

        @JsonProperty("Currency")
        private String currency;

        @JsonProperty("BankName")
        private String bankName;

        @JsonProperty("BankUrl")
        private String bankUrl;

        @JsonProperty("Balance")
        private String balance;

        @JsonProperty("InterestRate")
        private String interestRate;

        @JsonProperty("MonthlyPayment")
        private String monthlyPayment;

        @JsonProperty("NextPaymentDue")
        private String nextPaymentDue;

        @JsonProperty("Title")
        private String title;

        // Getters and setters for all account fields
        public String getAccountId() {
            return accountId;
        }

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAccountType() {
            return accountType;
        }

        public void setAccountType(String accountType) {
            this.accountType = accountType;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getBankUrl() {
            return bankUrl;
        }

        public void setBankUrl(String bankUrl) {
            this.bankUrl = bankUrl;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getInterestRate() {
            return interestRate;
        }

        public void setInterestRate(String interestRate) {
            this.interestRate = interestRate;
        }

        public String getMonthlyPayment() {
            return monthlyPayment;
        }

        public void setMonthlyPayment(String monthlyPayment) {
            this.monthlyPayment = monthlyPayment;
        }

        public String getNextPaymentDue() {
            return nextPaymentDue;
        }

        public void setNextPaymentDue(String nextPaymentDue) {
            this.nextPaymentDue = nextPaymentDue;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
