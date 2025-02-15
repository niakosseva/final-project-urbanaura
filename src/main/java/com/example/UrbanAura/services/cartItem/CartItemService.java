package com.example.UrbanAura.services.cartItem;

import com.example.UrbanAura.models.dtos.CartItemDTO;
import com.example.UrbanAura.models.entities.CartItem;

import java.util.List;

public interface CartItemService {

    void addItemToCart(Long cartId, Long itemId, int quantity);
    void removeItemFromCart(Long cartId, Long itemId);
    void updateItemQuantity(Long cartId, Long itemId, int quantity);

    CartItem getCartItem(Long cartId, Long itemId);

    int getCartItemCount(Long cartId);

    List<CartItemDTO> getConvertedCartItems(List<CartItem> cartItems);

    CartItemDTO convertToDTO(CartItem cartItem);

    List<CartItem> findByCartId(Long cartId);
}
