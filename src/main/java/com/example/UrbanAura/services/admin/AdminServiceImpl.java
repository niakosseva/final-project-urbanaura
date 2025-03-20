package com.example.UrbanAura.services.admin;

import com.example.UrbanAura.exceptions.ResourceNotFoundException;
import com.example.UrbanAura.models.dtos.UserDetailsDTO;
import com.example.UrbanAura.models.entities.Role;
import com.example.UrbanAura.models.entities.User;
import com.example.UrbanAura.repositories.RoleRepository;
import com.example.UrbanAura.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;


    public AdminServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<UserDetailsDTO> getConvertedUsers(List<User> users) {


        return users.stream().map(this::convertToDTO).toList();
    }

    @Override
    public UserDetailsDTO convertToDTO(User user) {
        UserDetailsDTO dto = modelMapper.map(user, UserDetailsDTO.class);

        List<String> roleNames = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());
        dto.setRoles(roleNames);
        return dto;
    }

    @Override
    public void changeRoles(String email, String roleName) {
        User user = userRepository.findOptionalByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new ResourceNotFoundException("Admin role not found!"));
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new ResourceNotFoundException("User role not found!"));
        Set<Role> roles = new HashSet<>(user.getRoles());

        // Ако потребителят има ROLE_ADMIN и искаме да го сменим на ROLE_USER:
        if (roles.contains(adminRole)) {
            System.out.println("Admin Role changed");
            roles.remove(adminRole);
            roles.add(userRole);
        } else if (roles.contains(userRole)) {
            System.out.println("User Role changed");
            roles.remove(userRole);
            roles.add(adminRole);
        }
        user.setRoles(roles);
        userRepository.save(user);
    }


}
