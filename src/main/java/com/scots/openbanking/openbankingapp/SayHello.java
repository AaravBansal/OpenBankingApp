package com.scots.openbanking.openbankingapp;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class SayHello {

    @GetMapping("/planets")
    public ResponseEntity<String> getPlanets() {
        WebClient client = WebClient.create();
        String result = client.get()
                .uri("https://deckofcardsapi.com/api/deck/new/shuffle/?deck_count=1")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return ResponseEntity.ok(result);
    }
}
