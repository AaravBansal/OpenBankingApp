package com.scots.openbanking.openbankingapp;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class SayHello {

    @GetMapping("/westpac")
    public String index() {
        return "Greetings from Spring Boot!";
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

}
