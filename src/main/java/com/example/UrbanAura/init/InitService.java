package com.example.UrbanAura.init;

import com.example.UrbanAura.models.entities.UserRole;
import com.example.UrbanAura.models.enums.Role;
import com.example.UrbanAura.repositories.UserRoleRepository;
import jakarta.annotation.PostConstruct;

public class InitService {

    private final UserRoleRepository userRoleRepository;

    public InitService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }


    @PostConstruct
    public void init() {

        initRoles();
    }

    private void initRoles() {
        if (userRoleRepository.count() == 0) {
            UserRole admin = new UserRole().setRole(Role.ADMIN);
            UserRole user = new UserRole().setRole(Role.USER);
            userRoleRepository.save(admin);
            userRoleRepository.save(user);
        }
    }
}
