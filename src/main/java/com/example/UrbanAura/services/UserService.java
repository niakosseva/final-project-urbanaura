package com.example.UrbanAura.services;

import com.example.UrbanAura.exceptions.EmailAlreadyExistsException;
import com.example.UrbanAura.models.dtos.UserRegistrationDTO;


public interface UserService {

    void registerUser(UserRegistrationDTO userRegistration) throws EmailAlreadyExistsException;
    boolean existsByEmail(String email);


}
