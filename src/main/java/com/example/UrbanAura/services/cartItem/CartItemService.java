package com.example.UrbanAura.services.cartItem;

import com.example.UrbanAura.models.entities.CartItem;

public interface CartItemService {

    void addItemToCart(Long cartId, Long itemId, int quantity);
    void removeItemFromCart(Long cartId, Long itemId);
    void updateItemQuantity(Long cartId, Long itemId, int quantity);

    CartItem getCartItem(Long cartId, Long itemId);

}
