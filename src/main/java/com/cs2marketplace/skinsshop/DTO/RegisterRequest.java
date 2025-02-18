package com.cs2marketplace.skinsshop.DTO;


import jakarta.validation.constraints.NotBlank;

public class RegisterRequest {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Email cannot be empty")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    private String password;

    public @NotBlank(message = "Name cannot be empty") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name cannot be empty") String name) {
        this.name = name;
    }

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