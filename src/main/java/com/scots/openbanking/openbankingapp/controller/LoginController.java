package com.scots.openbanking.openbankingapp.controller;

import com.scots.openbanking.openbankingapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> manualLogin(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        if (email == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("status", "fail", "message", "Missing email or password"));
        }

        boolean valid = userService.checkCredentials(email, password);
        if (valid) {
            return ResponseEntity.ok(Map.of("status", "success"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("status", "fail", "message", "Invalid credentials"));
        }
    }
}
