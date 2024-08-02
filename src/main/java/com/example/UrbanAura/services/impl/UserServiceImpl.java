package com.example.UrbanAura.services.impl;


import com.example.UrbanAura.exceptions.EmailAlreadyExistsException;
import com.example.UrbanAura.models.dtos.UserRegistrationDTO;
import com.example.UrbanAura.models.entities.User;
import com.example.UrbanAura.repositories.UserRepository;
import com.example.UrbanAura.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public void registerUser(UserRegistrationDTO registrationDTO) throws EmailAlreadyExistsException {
        if (existsByEmail(registrationDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email already in use.");
        }

        User user = map(registrationDTO);
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        userRepository.save(map(registrationDTO));

    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    private User map(UserRegistrationDTO userRegistrationDTO) {
        User mappedUser = modelMapper.map(userRegistrationDTO, User.class);

        mappedUser.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        return mappedUser;

    }


}
