package com.scots.openbanking.openbankingapp;

import com.scots.openbanking.openbankingapp.auth.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class SayHello {

    @GetMapping("/token")
    public String index() throws Exception {
        TokenService tokenService = new TokenService();
        return tokenService.fetchAccessToken();
    }

    @GetMapping("/login")
    public String login(String username, String password) throws Exception {
        System.out.println(username + password);
        return username;
    }


    @GetMapping("/planets")
    public String getPlanets() {
        RestClient restClient = RestClient.create();
        ResponseEntity<String> result = restClient.get()
                .uri("https://deckofcardsapi.com/api/deck/new/shuffle/?deck_count=1")
                .retrieve()
                .toEntity(String.class);

        System.out.println("Response status: " + result.getStatusCode());
        System.out.println("Response headers: " + result.getHeaders());
        System.out.println("Contents: " + result.getBody());
        return result.getBody();
    }

    @GetMapping("/login/oauth2/code/google")
    public void google() {
        System.out.println("got your token from google");
    }

}
