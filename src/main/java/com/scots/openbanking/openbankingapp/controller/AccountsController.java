package com.scots.openbanking.openbankingapp.controller;

import com.scots.openbanking.openbankingapp.service.PaymentsNzService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AccountsController {

    private final PaymentsNzService paymentsNzService;

    public AccountsController(PaymentsNzService paymentsNzService) {
        this.paymentsNzService = paymentsNzService;
    }

    @GetMapping("/token")
    public ResponseEntity<String> getToken() throws Exception {
        String accessToken = paymentsNzService.getAccessToken();
        return ResponseEntity.ok(accessToken);
    }
}
