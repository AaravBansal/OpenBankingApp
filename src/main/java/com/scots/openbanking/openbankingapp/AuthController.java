//package com.scots.openbanking.openbankingapp;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.UUID;
//
//@RestController
//public class AuthController {
//
//
//    @GetMapping("/token")
//    public String getToken(@AuthenticationPrincipal OAuth2AuthenticationToken authToken) {
//
//        UUID uuid = UUID.randomUUID();
//        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
//                authToken.getAuthorizedClientRegistrationId(),
//                authToken.getName()
//        );
//        return client.getAccessToken().getTokenValue();
//    }
//}
