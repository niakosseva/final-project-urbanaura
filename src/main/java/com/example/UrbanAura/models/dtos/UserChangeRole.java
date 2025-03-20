package com.example.UrbanAura.models.dtos;

import com.example.UrbanAura.models.entities.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserChangeRole {
    private String email;
    private Set<Role> role;
}
