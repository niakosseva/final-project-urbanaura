package com.example.UrbanAura.services;

import com.example.UrbanAura.exceptions.EmailAndUsernameAlreadyExistsException;
import com.example.UrbanAura.models.dtos.UserRegistrationDTO;


public interface UserService {

    void registerUser(UserRegistrationDTO userRegistration) throws EmailAndUsernameAlreadyExistsException;


}
