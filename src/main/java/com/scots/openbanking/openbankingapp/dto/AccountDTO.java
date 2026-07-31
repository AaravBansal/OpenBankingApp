package com.scots.openbanking.openbankingapp.dto;


public class AccountDTO {


    private String accountId;

    private String nickname;

    private String accountType;

    private String currency;

    private Double balance;

    private String bankName;



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



    public Double getBalance() {
        return balance;
    }


    public void setBalance(Double balance) {
        this.balance = balance;
    }



    public String getBankName() {
        return bankName;
    }


    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

}