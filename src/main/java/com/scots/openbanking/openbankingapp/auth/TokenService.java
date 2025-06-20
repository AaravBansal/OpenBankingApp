package com.scots.openbanking.openbankingapp.auth;

import com.scots.openbanking.openbankingapp.config.LoggingRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

@Service
public class TokenService {

    @Value("${paymentsnz.client-id}")
    private String clientId;

    @Value("${paymentsnz.token-url}")
    private String tokenUrl;

    @Value("${paymentsnz.private-key-path}")
    private String privateKeyPath;

    public String fetchAccessToken() throws Exception {

        tokenUrl = "https://api-nomatls.apicentre.middleware.co.nz/oauth/v2.0/token";
        clientId = "00e97d9d67994d7abe46f36798b6faaa";
        privateKeyPath = "src/main/resources/private_key.pem";

        String clientAssertion = JwtUtil.generateClientAssertion(clientId, tokenUrl, privateKeyPath);

        System.out.println("JWT is " + clientAssertion);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // --- CORRECTED BODY CONSTRUCTION ---
        String body = "grant_type=client_credentials"
                + "&scope=" + URLEncoder.encode("openid accounts payments", StandardCharsets.UTF_8)
                + "&client_id=" + URLEncoder.encode(clientId, StandardCharsets.UTF_8) // Re-added client_id
                + "&client_assertion_type=" + URLEncoder.encode("urn:ietf:params:oauth:client-assertion-type:jwt-bearer", StandardCharsets.UTF_8)
                + "&client_assertion=" + URLEncoder.encode(clientAssertion, StandardCharsets.UTF_8); // Re-added URL encoding


        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(new LoggingRequestInterceptor()));

        ResponseEntity<Map> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, entity, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return (String) response.getBody().get("access_token");
        } else {
            throw new RuntimeException("Failed to retrieve token: " + response);
        }
    }
}
