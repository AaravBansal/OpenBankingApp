package com.scots.openbanking.openbankingapp.auth;

import com.scots.openbanking.openbankingapp.model.User;
import com.scots.openbanking.openbankingapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        // Extract user attributes from OAuth2 provider (Google)
        String email = oauth2User.getAttribute("email");
        String firstName = oauth2User.getAttribute("given_name");
        String lastName = oauth2User.getAttribute("family_name");

        if (email == null) {
            throw new OAuth2AuthenticationException("Email not found from OAuth2 provider");
        }

        // Check if user already exists
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            // Create and save a new user based on Google info
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setFirstName(firstName);
            newUser.setLastName(lastName);
            newUser.setPreferredName(firstName);  // Use given_name as preferred name

            userRepository.save(newUser);
        }

        return oauth2User;
    }
}
