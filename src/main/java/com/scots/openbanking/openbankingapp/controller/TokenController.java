package com.scots.openbanking.openbankingapp.controller;

import com.scots.openbanking.openbankingapp.auth.TokenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/token")
    public String getAccessToken() {
        try {
            return tokenService.getAccessToken();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching token: " + e.getMessage();
        }
    }
}
