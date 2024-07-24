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

    public void setEmail(@NotNull @Email String email) {
        this.email = email;
    }

    public @NotNull String getPassword() {
        return password;
    }

    public void setPassword(@NotNull String password) {
        this.password = password;
    }
}
