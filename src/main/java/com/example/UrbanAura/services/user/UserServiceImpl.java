package com.example.UrbanAura.services.user;

import com.example.UrbanAura.exceptions.AlreadyExistsException;
import com.example.UrbanAura.exceptions.ResourceNotFoundException;
import com.example.UrbanAura.models.dtos.UserDetailsDTO;
import com.example.UrbanAura.models.entities.User;
import com.example.UrbanAura.repositories.UserRepository;
import com.example.UrbanAura.requests.CreateUserRequest;
import com.example.UrbanAura.requests.UserUpdateRequest;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserServiceImpl(ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


//    @Override
//    public void registerUser(UserRegistrationDTO registrationDTO) throws EmailAndUsernameAlreadyExistsException {
//        boolean existsByEmail = userRepository.existsByEmail(registrationDTO.getEmail());
//        boolean existsByUsername = userRepository.existsByUsername(registrationDTO.getUsername());
//
//        if (existsByEmail || existsByUsername) {
//            throw new EmailAndUsernameAlreadyExistsException("Email or username already in use.");
//        }
//
//        User user = map(registrationDTO);
//        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
//        userRepository.save(map(registrationDTO));
//
//    }

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
                    return userRepository.save(user);
                }).orElseThrow(() -> new AlreadyExistsException("Oops!" + request.getEmail() + "already exists."));
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
        boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

        if (!isAdmin) {
            if (password == null || !passwordEncoder.matches(password, user.getPassword())) {
                throw new RuntimeException("Incorrect password!");
            }

            userRepository.delete(user);
        }
    }

    @Override
    public UserDetailsDTO convertUserToDto(User user) {
        if (user == null) {
            return null;
        }
        return modelMapper.map(user, UserDetailsDTO.class);
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

