package com.example.UrbanAura.services.user;

import com.example.UrbanAura.exceptions.AlreadyExistsException;
import com.example.UrbanAura.exceptions.ResourceNotFoundException;
import com.example.UrbanAura.models.dtos.UserDetailsDTO;
import com.example.UrbanAura.models.dtos.UserUpdateUsernameDTO;
import com.example.UrbanAura.models.entities.Role;
import com.example.UrbanAura.models.entities.User;
import com.example.UrbanAura.repositories.RoleRepository;
import com.example.UrbanAura.repositories.UserRepository;
import com.example.UrbanAura.requests.CreateUserRequest;
import com.example.UrbanAura.requests.UserUpdateRequest;
import com.example.UrbanAura.response.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request).filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    Role defaultRole = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new ResourceNotFoundException("Role not found"));
                    user.setRoles(Set.of(defaultRole));
                    return userRepository.save(user);
                }).orElseThrow(() -> new AlreadyExistsException("Oops! " + request.getEmail() + " already exists."));

    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {
        return userRepository.findById(userId)
                .map(existingUser -> {
                    existingUser.setFirstName(request.getFirstName());
                    existingUser.setLastName(request.getLastName());
                    return userRepository.save(existingUser);
                }).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }


    @Override
    public void deleteUser(Long userId, String password) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            if (password == null || !passwordEncoder.matches(password, user.getPassword())) {
                throw new RuntimeException("Incorrect password!");
            }
        }

        if (isAdmin && auth.getName().equals(user.getFirstName())) {
            throw new RuntimeException("Admin cannot delete themselves!");
        }

        userRepository.deleteById(user.getId());
    }


    @Override
    public UserDetailsDTO convertUserToDto(User user) {
        if (user == null) {
            return null;
        }
        return modelMapper.map(user, UserDetailsDTO.class);
    }

    @Override
    public UserUpdateUsernameDTO convertUserUpdateToDto(User user) {
        if (user == null) {
            return null;
        }
        return modelMapper.map(user, UserUpdateUsernameDTO.class);
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }

    @Override
    public String getFirstNameByEmail(String email) {
        return userRepository.findOptionalByEmail(email)
                .map(User::getFirstName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + email));
    }



    //    private User map(UserRegistrationDTO userRegistrationDTO) {
//        User mappedUser = modelMapper.map(userRegistrationDTO, User.class);
//
//        mappedUser.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
//        return mappedUser;
//
//    }
//


}

