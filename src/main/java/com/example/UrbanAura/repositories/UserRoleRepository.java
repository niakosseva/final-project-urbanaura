package com.example.UrbanAura.repositories;

import com.example.UrbanAura.models.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<Role, Long> {
}
