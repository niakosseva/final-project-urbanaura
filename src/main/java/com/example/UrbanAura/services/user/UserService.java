package com.example.UrbanAura.services.user;

import com.example.UrbanAura.exceptions.EmailAndUsernameAlreadyExistsException;
import com.example.UrbanAura.models.dtos.UserDetailsDTO;
import com.example.UrbanAura.models.dtos.UserRegistrationDTO;
import com.example.UrbanAura.models.entities.User;
import com.example.UrbanAura.requests.CreateUserRequest;
import com.example.UrbanAura.requests.UserUpdateRequest;


public interface UserService {

//    void registerUser(UserRegistrationDTO userRegistration) throws EmailAndUsernameAlreadyExistsException;

    User getUserById(Long userId);

    User createUser(CreateUserRequest user);

    User updateUser(UserUpdateRequest request, Long userId);

    void deleteUser(Long userId, String password);

    UserDetailsDTO convertUserToDto(User user);

    User getAuthenticatedUser();

    String getFirstNameByEmail(String email);

}
