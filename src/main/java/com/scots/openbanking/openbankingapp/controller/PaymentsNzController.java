package com.scots.openbanking.openbankingapp.controller;

import com.scots.openbanking.openbankingapp.auth.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/payments-nz")
public class PaymentsNzController {

    @Autowired
    private TokenService tokenService;

    @GetMapping("/accounts")
    public ResponseEntity<String> getAccounts() throws Exception {

        // read the json file and return



        // Step 1: Get access token
        String accessToken = tokenService.fetchAccessToken();

        // Step 2: Prepare HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        RestTemplate restTemplate = new RestTemplate();

        // Step 3: Replace with actual Payments NZ endpoint for accounts
        String url = "https://sandbox.api.apicentre.paymentsnz.co.nz/open-banking/accounts/v3.0/accounts";

        // Step 4: Make the call
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        return response;
    }
}
