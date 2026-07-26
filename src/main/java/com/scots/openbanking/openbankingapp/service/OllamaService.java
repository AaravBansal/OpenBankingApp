package com.scots.openbanking.openbankingapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class OllamaService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();


    public String askAI(String prompt) throws Exception {

        String url = "http://localhost:11434/api/generate";


        Map<String, Object> request = Map.of(
                "model", "qwen3:8b",
                "prompt", prompt,
                "stream", false
        );


        String rawResponse = restTemplate.postForObject(
                url,
                request,
                String.class
        );


        JsonNode jsonResponse = objectMapper.readTree(rawResponse);


        return jsonResponse
                .get("response")
                .asText();
    }
}