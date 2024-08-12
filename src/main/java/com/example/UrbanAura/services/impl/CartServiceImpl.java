package com.example.UrbanAura.services.impl;

import com.example.UrbanAura.exceptions.ResourceNotFoundException;
import com.example.UrbanAura.models.entities.Cart;
import com.example.UrbanAura.models.entities.CartItem;
import com.example.UrbanAura.models.entities.Item;
import com.example.UrbanAura.repositories.CartItemRepository;
import com.example.UrbanAura.repositories.CartRepository;
import com.example.UrbanAura.repositories.ItemRepository;
import com.example.UrbanAura.services.CartService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;

    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository, ItemRepository itemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.itemRepository = itemRepository;
    }


    @Override
    public Cart getCart(Long id) {
        return cartRepository.findUserById(id);
    }

    @Override
    public void addItemToCart(Long id, Long itemId, int quantity) {
        Cart cart = cartRepository.findUserById(id);
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setItem(item);
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
    }

    @Override
    public void removeItemFromCart(Long id, Long itemId) {
        Cart cart = cartRepository.findUserById(id);
        CartItem cartItem = cart.getCartItem()
                .stream()
                .filter(item ->
                        item.getItem().getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found."));
        cartItemRepository.delete(cartItem);
    }

    @Override
    public Set<CartItem> getCartItems(Long id) {
        Cart cart = cartRepository.findUserById(id);

        return cart.getCartItem();
    }
}
