package com.example.UrbanAura.services.impl;


import com.example.UrbanAura.exceptions.EmailAndUsernameAlreadyExistsException;
import com.example.UrbanAura.models.dtos.UserRegistrationDTO;
import com.example.UrbanAura.models.entities.User;
import com.example.UrbanAura.repositories.UserRepository;
import com.example.UrbanAura.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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


    @Override
    public void registerUser(UserRegistrationDTO registrationDTO) throws EmailAndUsernameAlreadyExistsException {
        boolean existsByEmail = userRepository.existsByEmail(registrationDTO.getEmail());
        boolean existsByUsername = userRepository.existsByUsername(registrationDTO.getUsername());

        if(existsByEmail || existsByUsername) {
            throw new EmailAndUsernameAlreadyExistsException("Email or username already in use.");
        }


        User user = map(registrationDTO);
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        userRepository.save(map(registrationDTO));

    }






    private User map(UserRegistrationDTO userRegistrationDTO) {
        User mappedUser = modelMapper.map(userRegistrationDTO, User.class);

        mappedUser.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        return mappedUser;

    }


}
