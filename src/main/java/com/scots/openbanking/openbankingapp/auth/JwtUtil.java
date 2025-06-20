package com.scots.openbanking.openbankingapp.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class JwtUtil {

    public static String generateClientAssertion(String clientId, String tokenUrl, String keyPath) throws Exception {
        PrivateKey privateKey = loadPrivateKey(keyPath);

        long now = System.currentTimeMillis();
        long exp = now + (5 * 60 * 1000); // 5 minutes

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("kid", "01bbd73f-97da-4306-85e0-e00e067a17fd")
                .setIssuer(clientId)
                .setSubject(clientId)
                //.setAudience(tokenUrl)
                .setAudience("http://localhost:8080")
                .setExpiration(new Date(exp))
                .setIssuedAt(new Date(now))
                .setId(UUID.randomUUID().toString())
                .signWith(privateKey, SignatureAlgorithm.PS256)
                .compact();
    }

    private static PrivateKey loadPrivateKey(String filename) throws Exception {
        String key = new String(Files.readAllBytes(Paths.get(filename)));
        key = key.replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");
        byte[] keyBytes = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }
}
