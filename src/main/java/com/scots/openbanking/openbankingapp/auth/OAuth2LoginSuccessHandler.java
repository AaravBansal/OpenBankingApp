package com.scots.openbanking.openbankingapp.auth;

import com.scots.openbanking.openbankingapp.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;

    public OAuth2LoginSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oauthUser.getAttributes();

        String email = (String) attributes.get("email");
        String firstName = (String) attributes.getOrDefault("given_name", "");
        String lastName = (String) attributes.getOrDefault("family_name", "");
        String preferredName = firstName;

        userService.processOAuthPostLogin(email, firstName, lastName, preferredName);

        // Redirect to React dashboard URL
        response.sendRedirect("http://localhost:3000/dashboard");
    }
}
