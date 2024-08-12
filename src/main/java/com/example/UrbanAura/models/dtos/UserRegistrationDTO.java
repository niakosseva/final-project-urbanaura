package com.example.UrbanAura.models.dtos;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserRegistrationDTO {

    @NotEmpty(message = "Username name is required")
    @Size(min = 3, max = 15)
    private String username;

    @NotEmpty(message = "Email is required")
    @Email
    private String email;

    @NotEmpty
    @Size(min = 6, max = 15, message = "Password must be between 6 and 15 characters.")
    private String password;

    @NotEmpty
    @Size
    private String matchingPassword;

    public @NotEmpty(message = "Username is required") @Size(min = 3, max = 15) String getUsername() {
        return username;
    }

    public void setUsername(@NotEmpty(message = "Username is required") @Size(min = 3, max = 15) String username) {
        this.username = username;
    }

    @AssertTrue(message = "Password do not match")
    public boolean passwordsMatch() {
        return this.password.equals(this.matchingPassword);
    }


    public @NotEmpty(message = "Email is required.") @Email String getEmail() {
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
