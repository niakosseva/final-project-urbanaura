package com.example.UrbanAura.repositories;


import com.example.UrbanAura.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//
    boolean existsByEmail(String email);

    User findByEmail(String email);
   Optional <User> findOptionalByEmail(String email);

    void deleteById(User user);

    User findByFirstName(String firstName);
}
