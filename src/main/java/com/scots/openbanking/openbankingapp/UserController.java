package com.scots.openbanking.openbankingapp;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {

    @GetMapping("/me")
    public Map<String, String> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return Map.of("username", "Not logged in");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            return Map.of("username", userDetails.getUsername());
        } else if (principal instanceof OAuth2User oauthUser) {
            String name = (String) oauthUser.getAttributes().getOrDefault("name", "Google User");
            return Map.of("username", name);
        }

        return Map.of("username", "Unknown user");
    }
}
