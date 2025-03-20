package com.example.UrbanAura.services.admin;

import com.example.UrbanAura.models.dtos.OrderDTO;
import com.example.UrbanAura.models.dtos.UserChangeRole;
import com.example.UrbanAura.models.dtos.UserDetailsDTO;

import com.example.UrbanAura.models.entities.Role;
import com.example.UrbanAura.models.entities.User;

import java.util.List;
import java.util.Set;

public interface AdminService {
    List<User> getAllUsers();
    List<UserDetailsDTO> getConvertedUsers (List<User> users);
    UserDetailsDTO convertToDTO(User user);

    void changeRoles(String email, String roleName);
}
