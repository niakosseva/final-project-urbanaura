package com.example.UrbanAura.services;

import com.example.UrbanAura.models.entities.User;
import com.example.UrbanAura.models.entities.UserRole;
import com.example.UrbanAura.models.enums.Role;
import com.example.UrbanAura.repositories.UserRepository;
import com.example.UrbanAura.user.UrbanAuraUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class AppUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;

    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email! " + username));
    }


    private UserDetails map(User user) {
        return new UrbanAuraUserDetails(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(UserRole::getRole).map(AppUserDetailsService::map).toList(),
                user.getId(),
                user.getEmail()



        );
    }

    private static GrantedAuthority map(Role role) {
        return new SimpleGrantedAuthority("ROLE_" + role);

    }


//    public User getAuthenticatedUser() {
//        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        final HttpUserDetails userDetails = (HttpUserDetails)authentication.getPrincipal();
//        return userRepository.findById(userDetails.getId()).get();
//    }

}