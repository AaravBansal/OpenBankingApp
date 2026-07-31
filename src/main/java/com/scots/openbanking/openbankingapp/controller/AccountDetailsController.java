package com.scots.openbanking.openbankingapp.controller;


import com.scots.openbanking.openbankingapp.dto.AccountDTO;
import org.springframework.web.bind.annotation.*;


import java.util.List;



@RestController
@RequestMapping("/api/accounts")
public class AccountDetailsController {



    private final AccountDetailsService accountDetailsService;



    public AccountDetailsController(
            AccountDetailsService accountDetailsService
    ){

        this.accountDetailsService =
                accountDetailsService;

    }




    @GetMapping
    public List<AccountDTO> getAccounts()
            throws Exception {


        return accountDetailsService.getCombinedAccounts();

    }

}