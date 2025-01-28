//package com.example.UrbanAura.init;
//
//import com.example.UrbanAura.models.entities.Role;
//import com.example.UrbanAura.models.enums.Roles;
//import com.example.UrbanAura.repositories.UserRoleRepository;
//import jakarta.annotation.PostConstruct;
//
//public class InitService {
//
//    private final UserRoleRepository userRoleRepository;
//
//    public InitService(UserRoleRepository userRoleRepository) {
//        this.userRoleRepository = userRoleRepository;
//    }
//
//
//    @PostConstruct
//    public void init() {
//
//        initRoles();
//    }
//
//    private void initRoles() {
//        if (userRoleRepository.count() == 0) {
//            Role admin = new Role().setName(Roles.ADMIN);
//            Role user = new Role().setName(Roles.USER);
//            userRoleRepository.save(admin);
//            userRoleRepository.save(user);
//        }
//    }
//}
