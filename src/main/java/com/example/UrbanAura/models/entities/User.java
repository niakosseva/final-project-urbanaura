package com.example.UrbanAura.models.entities;


import com.example.UrbanAura.models.BaseEntity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(nullable = false)
    @NotBlank
    private String fullName;

    @Column(nullable = false, unique = true)
    @NotBlank
    private String email;

//    @Column(nullable = false)
//    @NotBlank
//    private String firstName;
//    @Column(nullable = false)
//    @NotBlank
//    private String lastName;


    @Column(nullable = false)
    @NotBlank
    private String password;



//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "address_id", referencedColumnName = "id")
//    private Address address;

    @OneToMany(mappedBy = "user")
    private Set<Item> items = new HashSet<>();

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

    public @NotBlank String getFullName() {
        return fullName;
    }

    public void setFullName(@NotBlank String fullName) {
        this.fullName = fullName;
    }


//    public Address getAddress() {
//        return address;
//    }
//
//    public void setAddress(Address address) {
//        this.address = address;
//    }

    public List<UserRole> getRoles() {
        return roles;
    }

//    public @NotBlank String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(@NotBlank String firstName) {
//        this.firstName = firstName;
//    }
//
//    public @NotBlank String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(@NotBlank String lastName) {
//        this.lastName = lastName;
//    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

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

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}
