package com.cs2marketplace.skinsshop.controllers;


import com.cs2marketplace.skinsshop.DTO.LoginRequest;
import com.cs2marketplace.skinsshop.DTO.RegisterRequest;
import com.cs2marketplace.skinsshop.model.User;
import com.cs2marketplace.skinsshop.services.AuthService;
import com.cs2marketplace.skinsshop.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.authenticate(loginRequest);
        return ResponseEntity.ok().body("{\"token\": \"" + token + "\"}");
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterRequest registerRequest) {
        User newUser  = userService.registerUser (registerRequest);
        return ResponseEntity.ok(newUser );
    }
}
