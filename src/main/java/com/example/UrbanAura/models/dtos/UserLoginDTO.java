package com.example.UrbanAura.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;


public class UserLoginDTO {

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

    public @NotNull @Email String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
