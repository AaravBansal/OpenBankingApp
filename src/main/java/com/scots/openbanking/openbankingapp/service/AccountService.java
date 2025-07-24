package com.scots.openbanking.openbankingapp.service;

import com.scots.openbanking.openbankingapp.auth.TokenService;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class AccountService {

    private final RestTemplate restTemplate = new RestTemplate();

    private final TokenService tokenService;

    public AccountService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public Object getBalances(String accountId) throws Exception {
        String accessToken = tokenService.getAccessToken();
        String url = "https://api-nomatls.apicentre.middleware.co.nz/accounts/" + accountId + "/balances";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.set("x-fapi-financial-id", "FINANCIAL_ID");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
        return response.getBody();
    }

    public List<Map<String, Object>> getAccounts() throws Exception {
        String accessToken = tokenService.getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        String accountsUrl = "https://api-nomatls.apicentre.middleware.co.nz/accounts";

        ResponseEntity<Map> response = restTemplate.exchange(accountsUrl, HttpMethod.GET, requestEntity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            Object data = response.getBody().get("data");
            if (data instanceof List) {
                return (List<Map<String, Object>>) data;
            }
        }
        throw new RuntimeException("Failed to retrieve accounts");
    }
}
