package com.scots.openbanking.openbankingapp.controller;

import com.scots.openbanking.openbankingapp.model.User;
import com.scots.openbanking.openbankingapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user) {
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already registered");
        }
        User savedUser = userService.createUser(user);
        return ResponseEntity.ok(savedUser);
    }
}
