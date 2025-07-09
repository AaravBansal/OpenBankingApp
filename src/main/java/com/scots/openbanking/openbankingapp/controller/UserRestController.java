package com.scots.openbanking.openbankingapp.controller;

import com.scots.openbanking.openbankingapp.LoginRequest;
import com.scots.openbanking.openbankingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserRestController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        boolean valid = userService.checkCredentials(loginRequest.getEmail(), loginRequest.getPassword());
        if (valid) {
            return ResponseEntity.ok().body("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }
}
