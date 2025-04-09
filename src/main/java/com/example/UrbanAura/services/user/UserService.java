package com.example.UrbanAura.services.user;

import com.example.UrbanAura.models.dtos.UserDetailsDTO;
import com.example.UrbanAura.models.dtos.UserUpdateUsernameDTO;
import com.example.UrbanAura.models.entities.User;
import com.example.UrbanAura.requests.CreateUserRequest;
import com.example.UrbanAura.requests.UserUpdateRequest;


public interface UserService {

    User getUserById(Long userId);

    User createUser(CreateUserRequest user);

    User updateUser(UserUpdateRequest request, Long userId);

    void deleteUser(Long userId, String password);

    UserDetailsDTO convertUserToDto(User user);
    UserUpdateUsernameDTO convertUserUpdateToDto(User user);

    User getAuthenticatedUser();

}
