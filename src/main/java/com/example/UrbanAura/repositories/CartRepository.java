package com.example.UrbanAura.repositories;

import com.example.UrbanAura.models.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    Cart findUserById(Long id);
}
