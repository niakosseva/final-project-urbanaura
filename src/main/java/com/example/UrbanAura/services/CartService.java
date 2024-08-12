package com.example.UrbanAura.services;


import com.example.UrbanAura.models.entities.Cart;
import com.example.UrbanAura.models.entities.CartItem;

import java.util.List;
import java.util.Set;

public interface CartService {

    Cart getCart(Long id);
    void addItemToCart(Long id, Long itemId, int quantity);
    void removeItemFromCart(Long id, Long itemId);
    Set<CartItem> getCartItems(Long id);
}
