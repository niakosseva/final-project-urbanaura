package com.example.UrbanAura.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UrbanAuraUserDetails extends User {

   private final String fullName;

    public UrbanAuraUserDetails(
            String email,
            String password,
            Collection<? extends GrantedAuthority> authorities,
            String fullName
    ) {
        super(email, password, authorities);
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }



}
