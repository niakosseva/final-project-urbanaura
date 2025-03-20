package com.example.UrbanAura.models.entities;

import com.example.UrbanAura.models.BaseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "\"user\"")
public class User extends BaseEntity {
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
    private String address;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles = new HashSet<>();


    public User(String firstName, String lastName, String email, String password, String address, Cart cart, List<Order> orders, Collection<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.cart = cart;
        this.orders = orders;
        this.roles = roles;
    }


}
