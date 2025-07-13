package com.scots.openbanking.openbankingapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class BalancesWrapper {

    @JsonProperty("Data")
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {

        @JsonProperty("Balance")
        private List<Balance> balance;

        public List<Balance> getBalance() {
            return balance;
        }

        public void setBalance(List<Balance> balance) {
            this.balance = balance;
        }
    }

    public static class Balance {

        @JsonProperty("AccountId")
        private String accountId;

        @JsonProperty("Amount")
        private Amount amount;

        @JsonProperty("CreditDebitIndicator")
        private String creditDebitIndicator;

        @JsonProperty("Type")
        private String type;

        @JsonProperty("DateTime")
        private String dateTime;

        @JsonProperty("CreditLine")
        private List<CreditLine> creditLine;

        // Getters and setters

        public String getAccountId() {
            return accountId;
        }

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

        public Amount getAmount() {
            return amount;
        }

        public void setAmount(Amount amount) {
            this.amount = amount;
        }

        public String getCreditDebitIndicator() {
            return creditDebitIndicator;
        }

        public void setCreditDebitIndicator(String creditDebitIndicator) {
            this.creditDebitIndicator = creditDebitIndicator;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public List<CreditLine> getCreditLine() {
            return creditLine;
        }

        public void setCreditLine(List<CreditLine> creditLine) {
            this.creditLine = creditLine;
        }
    }

    public static class Amount {

        @JsonProperty("Amount")
        private String amount;

        @JsonProperty("Currency")
        private String currency;

        // Getters and setters

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }
    }

    public static class CreditLine {

        @JsonProperty("Included")
        private boolean included;

        @JsonProperty("Amount")
        private Amount amount;

        @JsonProperty("Type")
        private String type;

        // Getters and setters

        public boolean isIncluded() {
            return included;
        }

        public void setIncluded(boolean included) {
            this.included = included;
        }

        public Amount getAmount() {
            return amount;
        }

        public void setAmount(Amount amount) {
            this.amount = amount;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
