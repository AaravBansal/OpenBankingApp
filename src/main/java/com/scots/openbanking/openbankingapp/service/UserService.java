package com.scots.openbanking.openbankingapp.service;

import com.scots.openbanking.openbankingapp.model.User;
import com.scots.openbanking.openbankingapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setGoogleUser(false);
        user.setRegistered(true);
        return userRepository.save(user);
    }

    public boolean checkCredentials(String email, String rawPassword) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return false;
        }
        User user = userOpt.get();
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities("USER")
                .build();
    }

    public void processOAuthPostLogin(String email, String firstName, String lastName, String preferredName) {
        Optional<User> existUser = userRepository.findByEmail(email);

        if (existUser.isEmpty()) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setFirstName(firstName);
            newUser.setLastName(lastName);
            newUser.setPreferredName(preferredName);
            newUser.setGoogleUser(true);
            newUser.setPassword(""); // OAuth users don't need password
            newUser.setRegistered(true);
            userRepository.save(newUser);
        }
    }
}
