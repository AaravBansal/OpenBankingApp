package com.scots.openbanking.openbankingapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scots.openbanking.openbankingapp.dto.TransactionDTO;
import com.scots.openbanking.openbankingapp.model.TransactionsWrapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


@Service
public class TransactionService {


    private final ObjectMapper mapper = new ObjectMapper();



    public List<TransactionDTO> getTransactions() throws Exception {


        InputStream inputStream =
                new ClassPathResource(
                        "mockdata/transactions.json"
                ).getInputStream();



        TransactionsWrapper wrapper =
                mapper.readValue(
                        inputStream,
                        TransactionsWrapper.class
                );



        List<TransactionDTO> transactions =
                new ArrayList<>();



        for(
                TransactionsWrapper.Transaction transaction :
                wrapper.getData().getTransaction()
        ){


            TransactionDTO dto =
                    new TransactionDTO();



            dto.setTransactionId(
                    transaction.getTransactionId()
            );


            dto.setAccountId(
                    transaction.getAccountId()
            );


            dto.setDateTime(
                    transaction.getDate()
            );


            dto.setAmount(
                    Double.valueOf(String.valueOf(transaction.getAmount()))
            );


            dto.setCurrency(
                    transaction.getCurrency()
            );


            dto.setCreditDebitIndicator(
                    transaction.getType()
            );


            dto.setCategory(
                    transaction.getCategory()
            );


            dto.setDescription(
                    transaction.getDescription()
            );


            transactions.add(dto);

        }



        return transactions;

    }
    public String getTransactionsAsText() throws Exception {


        StringBuilder builder =
                new StringBuilder();



        getTransactions()

                .forEach(transaction -> {


                    builder.append(
                            """
                            
                            Transaction:
                            Date: %s
                            Merchant: %s
                            Category: %s
                            Amount: %s
                            Type: %s
                            
                            """
                                    .formatted(
                                            transaction.getDate(),
                                            transaction.getMerchant(),
                                            transaction.getCategory(),
                                            transaction.getAmount(),
                                            transaction.getCreditDebitIndicator()
                                    )
                    );


                });



        return builder.toString();

    }
}