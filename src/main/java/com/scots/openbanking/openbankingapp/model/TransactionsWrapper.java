package com.scots.openbanking.openbankingapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionsWrapper {


    @JsonProperty("Data")
    private Data data;


    public Data getData() {
        return data;
    }


    public void setData(Data data) {
        this.data = data;
    }



    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {


        @JsonProperty("Transaction")
        private List<Transaction> transaction;


        public List<Transaction> getTransaction() {
            return transaction;
        }


        public void setTransaction(List<Transaction> transaction) {
            this.transaction = transaction;
        }

    }



    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Transaction {


        private String id;

        private String transactionId;

        private String accountId;

        private String date;

        private String merchant;

        private String category;

        private double amount;

        private String currency;

        private String type;

        private String description;



        public String getId() {
            return id;
        }


        public void setId(String id) {
            this.id = id;
        }



        public String getTransactionId() {
            return transactionId;
        }


        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }



        public String getAccountId() {
            return accountId;
        }


        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }



        public String getDate() {
            return date;
        }


        public void setDate(String date) {
            this.date = date;
        }



        public String getMerchant() {
            return merchant;
        }


        public void setMerchant(String merchant) {
            this.merchant = merchant;
        }



        public String getCategory() {
            return category;
        }


        public void setCategory(String category) {
            this.category = category;
        }



        public double getAmount() {
            return amount;
        }


        public void setAmount(double amount) {
            this.amount = amount;
        }



        public String getCurrency() {
            return currency;
        }


        public void setCurrency(String currency) {
            this.currency = currency;
        }



        public String getType() {
            return type;
        }


        public void setType(String type) {
            this.type = type;
        }



        public String getDescription() {
            return description;
        }


        public void setDescription(String description) {
            this.description = description;
        }

    }
}