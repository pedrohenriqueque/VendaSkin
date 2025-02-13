package com.cs2marketplace.skinsshop.controllers;


import com.cs2marketplace.skinsshop.DTO.LoginRequest;
import com.cs2marketplace.skinsshop.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.authenticate(loginRequest);
        return ResponseEntity.ok().body("{\"token\": \"" + token + "\"}");
    }
}
