package com.example.UrbanAura.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserRegistrationDTO {

    @NotEmpty
    @Size(min = 2, max = 20)
    private String fullName;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty(message = "Password must contain minimum of 10 characters.")
    @Size(min = 10)
    private String password;
    @NotEmpty
    private String matchingPassword;

    public boolean passwordsMatch() {
        return this.password != null && this.password.equals(this.matchingPassword);
    }


    public @NotEmpty @Size(min = 2, max = 20) String getFullName() {
        return fullName;
    }

    public void setFullName(@NotEmpty @Size(min = 2, max = 20) String fullName) {
        this.fullName = fullName;
    }

    public @NotEmpty @Email(message = "Email must contain minimum of 12 characters.") String getEmail() {
        return email;
    }

    public void setEmail(@NotEmpty @Email(message = "Email must contain minimum of 12 characters.") String email) {
        this.email = email;
    }

    public @NotEmpty String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty String password) {
        this.password = password;
    }

    public @NotEmpty String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(@NotEmpty String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }
}
