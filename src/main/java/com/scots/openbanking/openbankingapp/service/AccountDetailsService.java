package com.scots.openbanking.openbankingapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scots.openbanking.openbankingapp.dto.AccountDTO;
import com.scots.openbanking.openbankingapp.model.AccountsWrapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


@Service
public class AccountDetailsService {


    private final ObjectMapper objectMapper;


    public AccountDetailsService(
            ObjectMapper objectMapper
    ) {
        this.objectMapper = objectMapper;
    }



    public List<AccountDTO> getCombinedAccounts() throws Exception {


        InputStream inputStream =
                new ClassPathResource(
                        "mockdata/accounts.json"
                )
                        .getInputStream();



        AccountsWrapper wrapper =
                objectMapper.readValue(
                        inputStream,
                        AccountsWrapper.class
                );



        List<AccountDTO> accounts =
                new ArrayList<>();



        for(AccountsWrapper.Account account :
                wrapper.getData().getAccount()) {



            AccountDTO dto =
                    new AccountDTO();



            dto.setAccountId(
                    account.getAccountId()
            );


            dto.setNickname(
                    account.getNickname()
            );


            dto.setAccountType(
                    account.getAccountType()
            );


            dto.setCurrency(
                    account.getCurrency()
            );


            dto.setBalance(
                    account.getBalance()
            );


            dto.setBankId(
                    account.getBankName()
            );

            accounts.add(dto);

        }



        return accounts;

    }

}