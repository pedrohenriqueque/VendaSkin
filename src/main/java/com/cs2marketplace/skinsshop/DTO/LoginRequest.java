package com.cs2marketplace.skinsshop.DTO;


import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank(message = "Email cannot be empty")
    private String email;
    @NotBlank(message = "Password cannot be empty")
    private String password;

    public @NotBlank(message = "Email cannot be empty") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email cannot be empty") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Password cannot be empty") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password cannot be empty") String password) {
        this.password = password;
    }
}
