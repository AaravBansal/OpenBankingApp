package com.scots.openbanking.openbankingapp.service;

import com.scots.openbanking.openbankingapp.dto.TransactionDTO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TransactionCacheService {

    private final TransactionService transactionService;
    private List<TransactionDTO> transactions;

    public TransactionCacheService(
            TransactionService transactionService
    ){

        this.transactionService = transactionService;
        refreshCache();
    }

    private void refreshCache(){

        try {
            transactions =
                    transactionService.getTransactions();

            System.out.println(
                    "Transactions cached: "
                            + transactions.size()
            );
        }
        catch(Exception e){

            e.printStackTrace();
            transactions = List.of();
        }
    }

    public List<TransactionDTO> getTransactions(){
        return transactions;
    }

}