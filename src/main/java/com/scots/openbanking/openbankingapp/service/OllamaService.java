package com.scots.openbanking.openbankingapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class OllamaService {

    // Ollama's local generate endpoint accepts the prompt as a JSON request.
    private static final String OLLAMA_GENERATE_URL = "http://localhost:11434/api/generate";
    private static final String MODEL_NAME = "qwen3:8b";

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String askAI(String prompt) throws Exception {
        // Disable streaming because this application returns one completed
        // response from its REST endpoint rather than incremental tokens.
        Map<String, Object> request = Map.of(
                "model", MODEL_NAME,
                "prompt", prompt,
                "stream", false
        );

        // Request the model response as raw JSON so it can be parsed without
        // coupling this service to a larger response DTO.
        String rawResponse = restTemplate.postForObject(
                OLLAMA_GENERATE_URL,
                request,
                String.class
        );

        // Ollama returns generated text in the top-level "response" field.
        JsonNode jsonResponse = objectMapper.readTree(rawResponse);

        return jsonResponse.get("response").asText();
    }
}
