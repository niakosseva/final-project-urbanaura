package com.example.UrbanAura.services.cart;

import com.example.UrbanAura.models.dtos.CartDTO;
import com.example.UrbanAura.models.entities.Cart;
import com.example.UrbanAura.models.entities.User;

import java.math.BigDecimal;

public interface CartService {
    Cart getCart(Long id);

    void clearCart(Long id);

    BigDecimal getTotalPrice(Long id);

    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);

//    CartDTO getCartDTO (Long cartId);

}
