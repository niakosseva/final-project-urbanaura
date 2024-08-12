package com.example.UrbanAura.models.entities;


import com.example.UrbanAura.models.BaseEntity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(nullable = false)
    @NotBlank
    private String username;

    @Column(nullable = false, unique = true)
    @NotBlank
    private String email;


    @Column(nullable = false)
    @NotBlank
    private String password;

    @Setter
    @Getter
    @OneToMany(mappedBy = "user")
    private Set<Item> items = new HashSet<>();

    @Setter
    @Getter
    @ManyToMany(
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<UserRole> roles = new ArrayList<>();

    public User(String email, String password, Collection<? extends GrantedAuthority> authorities) {
        super();
    }

    public User() {

    }

//    public @NotBlank String getFullName() {
//        return fullName;
//    }
//
//    public void setFullName(@NotBlank String fullName) {
//        this.fullName = fullName;
//    }


    public @NotBlank String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank String email) {
        this.email = email;
    }

    public @NotBlank String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }

    public @NotBlank String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank String username) {
        this.username = username;
    }
}
