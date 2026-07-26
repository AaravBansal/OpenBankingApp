package com.scots.openbanking.openbankingapp.model;

import java.util.List;

public class AccountsWrapper {


    private Data Data;


    public Data getData() {
        return Data;
    }


    public void setData(Data data) {
        Data = data;
    }



    public static class Data {


        private List<Account> Account;


        public List<Account> getAccount() {
            return Account;
        }


        public void setAccount(List<Account> account) {
            Account = account;
        }

    }



    public static class Account {


        private String accountId;
        private String nickname;
        private String accountType;
        private String currency;
        private String bankName;
        private double balance;



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


        public String getBalance() {
            return balance;
        }


        public void setBalance(double balance) {
            this.balance = balance;
        }

    }
}