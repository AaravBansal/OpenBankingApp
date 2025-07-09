package com.scots.openbanking.openbankingapp.controller;

import com.scots.openbanking.openbankingapp.model.User;
import com.scots.openbanking.openbankingapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class MeController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/me")
    public User getCurrentUser(@AuthenticationPrincipal OAuth2User oauth2User) {
        String email = oauth2User.getAttribute("email");
        if (email == null) {
            throw new RuntimeException("Email not found in OAuth2User");
        }

        // Find the user in MongoDB by email and return it
        Optional<User> userOpt = userRepository.findByEmail(email);
        return userOpt.orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
}
