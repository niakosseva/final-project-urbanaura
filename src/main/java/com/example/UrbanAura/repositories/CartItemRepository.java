package com.example.UrbanAura.repositories;

import com.example.UrbanAura.models.entities.CartItem;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteAllByCartId(Long id);
    List<CartItem> findByCartId(Long cartId);



}
