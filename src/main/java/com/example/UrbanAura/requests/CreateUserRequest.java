package com.example.UrbanAura.requests;

import lombok.Data;

@Data
public class CreateUserRequest {
    public String firstName;
    public String lastName;
    public String email;
    public String password;
    public String matchPassword;

}
