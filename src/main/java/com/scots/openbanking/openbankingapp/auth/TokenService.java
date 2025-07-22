package com.scots.openbanking.openbankingapp.auth;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.util.*;

@Slf4j
@Service
public class TokenService {

    @Value("${paymentsnz.client-id}")
    private String clientId;

    @Value("${paymentsnz.token-uri}")
    private String tokenUri;

    @Value("${paymentsnz.private-key-pem-path}")
    private String privateKeyPemPath;

    @Value("${paymentsnz.key-id}")
    private String keyId;

    private final ResourceLoader resourceLoader;
    private PrivateKey privateKey;

    public TokenService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    public void init() throws Exception {
        loadPrivateKey();
    }

    private void loadPrivateKey() throws Exception {
        // Load private key using the configured path
        Resource resource = resourceLoader.getResource(privateKeyPemPath);
        try (InputStream is = resource.getInputStream()) {
            String key = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            key = key.replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");
            byte[] keyBytes = Base64.getDecoder().decode(key);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            this.privateKey = kf.generatePrivate(spec);
        }
        log.info("Private key loaded successfully");
    }

    public String getAccessToken() throws Exception {
        JWSSigner signer = new RSASSASigner(privateKey);
        Instant now = Instant.now();

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .issuer(clientId)
                .subject(clientId)
                .audience(tokenUri)  // use tokenUri from config here
                .issueTime(Date.from(now))
                .expirationTime(Date.from(now.plusSeconds(300)))
                .jwtID(UUID.randomUUID().toString())
                .build();

        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
                .type(JOSEObjectType.JWT)
                .keyID(keyId)
                .build();

        SignedJWT signedJWT = new SignedJWT(header, claimsSet);
        signedJWT.sign(signer);
        String clientAssertion = signedJWT.serialize();

        log.info("Generated client_assertion JWT: {}", clientAssertion);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "client_credentials");
        form.add("client_id", clientId);
        form.add("client_assertion_type", "urn:ietf:params:oauth:client-assertion-type:jwt-bearer");
        form.add("client_assertion", clientAssertion);
        form.add("scope", "accounts balances");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setCacheControl("no-cache");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(form, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(tokenUri, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Token response: {}", response.getBody());
            return response.getBody();  // parse JSON later to extract "access_token"
        } else {
            log.error("Token request failed: {}", response.getStatusCode());
            log.error("Response body: {}", response.getBody());
            throw new RuntimeException("Failed to obtain access token: " + response.getStatusCode());
        }
    }
}
