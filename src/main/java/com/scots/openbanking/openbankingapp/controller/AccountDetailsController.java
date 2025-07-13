package com.scots.openbanking.openbankingapp.controller;

import com.scots.openbanking.openbankingapp.dto.AccountDTO;
import com.scots.openbanking.openbankingapp.service.AccountDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account-details")
public class AccountDetailsController {

    private final AccountDetailsService accountDetailsService;

    public AccountDetailsController(AccountDetailsService accountDetailsService) {
        this.accountDetailsService = accountDetailsService;
    }

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAccountDetails() {
        try {
            List<AccountDTO> accounts = accountDetailsService.getCombinedAccounts();
            return ResponseEntity.ok(accounts);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
