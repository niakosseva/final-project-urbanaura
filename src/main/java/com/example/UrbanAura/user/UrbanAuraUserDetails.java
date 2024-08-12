package com.example.UrbanAura.user;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class UrbanAuraUserDetails extends User {


    @Getter
    private final Long id;
   private final String username;

    public UrbanAuraUserDetails(
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities, Long id,

            String email
    ) {
        super(username, password, authorities);
        this.id = id;
        this.username = username;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}
