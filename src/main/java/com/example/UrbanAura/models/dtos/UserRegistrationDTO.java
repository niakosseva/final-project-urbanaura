package com.example.UrbanAura.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserRegistrationDTO {

    @NotEmpty(message = "Full name is required.")
    @Size(min = 2, max = 20)
    private String fullName;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty(message = "Password must contain minimum of 6 characters.")
    @Size(min = 6, max = 15)
    private String password;

    @NotEmpty
    @Size
    private String matchingPassword;

    public boolean passwordsMatch() {
        return this.password.equals(this.matchingPassword);
    }


    public @NotEmpty @Size(min = 2, max = 20)
    String getFullName() {
        return fullName;
    }

    public void setFullName(@NotEmpty @Size(min = 2, max = 20) String fullName) {
        this.fullName = fullName;
    }

    public @NotEmpty @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotEmpty @Email String email) {
        this.email = email;
    }

    public @NotEmpty(message = "Password must contain minimum of 6 characters.") @Size(min = 6) String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty(message = "Password must contain minimum of 6 characters.") @Size(min = 6) String password) {
        this.password = password;
    }

    public @NotEmpty @Size String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(@NotEmpty @Size String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }
}
