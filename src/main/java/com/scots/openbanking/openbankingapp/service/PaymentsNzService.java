package com.scots.openbanking.openbankingapp.service;

import com.scots.openbanking.openbankingapp.auth.TokenService;
import org.springframework.stereotype.Service;

@Service
public class PaymentsNzService {

    private final TokenService tokenService;

    public PaymentsNzService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public String getAccessToken() throws Exception {
        return tokenService.getAccessToken();
    }
}
