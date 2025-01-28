package com.example.UrbanAura.models.entities;

import com.example.UrbanAura.models.BaseEntity.BaseEntity;
import com.example.UrbanAura.models.enums.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "roles")
public class Role extends BaseEntity {

    private String name;

    public Role(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users = new HashSet<>();


}
