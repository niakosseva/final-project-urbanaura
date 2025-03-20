package com.example.UrbanAura.services.cartItem;

import com.example.UrbanAura.exceptions.ResourceNotFoundException;
import com.example.UrbanAura.models.dtos.CartItemDTO;
import com.example.UrbanAura.models.dtos.ImageDTO;
import com.example.UrbanAura.models.entities.*;
import com.example.UrbanAura.repositories.CartItemRepository;
import com.example.UrbanAura.repositories.CartRepository;
import com.example.UrbanAura.services.cart.CartService;
import com.example.UrbanAura.services.item.ItemService;
import com.example.UrbanAura.services.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ItemService itemService;
    private final UserService userService;
    private final CartService cartService;
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, ItemService itemService, UserService userService, CartService cartService, CartRepository cartRepository, ModelMapper modelMapper) {
        this.cartItemRepository = cartItemRepository;
        this.itemService = itemService;
        this.userService = userService;
        this.cartService = cartService;
        this.cartRepository = cartRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public void addItemToCart(Long cartId, Long itemId, int quantity) {
        //1. Get the cart
        //2. Get the items
        //3. Check if the item already in the cart
        //4. If yes, then increase quantity with the requested quantity
        //5. If no, then initiate a new CartItem entry
        Cart cart = cartService.getCart(cartId);
        Item item = itemService.getItemById(itemId);

        CartItem cartItem = cart.getItems()
                .stream()
                .filter(i -> i.getItem().getId().equals(itemId))
                .findFirst()
                .orElse(new CartItem());

        if (cartItem.getId() == null) {
            cartItem.setCart(cart);
            cartItem.setItem(item);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(item.getPrice());
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);

    }

    @Override
    public void removeItemFromCart(Long cartId, Long itemId) {
        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemove = getCartItem(cartId, itemId);
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);

    }


    @Override
    public void updateItemQuantity(Long cartId, Long itemId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        cart.getItems()
                .stream()
                .filter(item -> item.getItem().getId().equals(itemId))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getItem().getPrice());
                    item.setTotalPrice();
                });
        BigDecimal totalAmount = cart.getItems()
                .stream().map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long itemId) {
        Cart cart = cartService.getCart(cartId);
        return cart.getItems()
                .stream()
                .filter(item -> item.getItem().getId().equals(itemId))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("Item not found"));


    }

    @Override
    public int getCartItemCount(Long cartId) {
        return cartItemRepository.findByCartId(cartId)
                .stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    @Override
    public List<CartItemDTO> getConvertedCartItems(List<CartItem> cartItems) {
        return cartItems.stream().map(this::convertToDTO).toList();
    }

    @Override
    public CartItemDTO convertToDTO(CartItem cartItem) {
        CartItemDTO cartItemDTO = modelMapper.map(cartItem, CartItemDTO.class);
        Item item = itemService.getItemById(cartItem.getItem().getId());
        List<Image> images = item.getImages();
        List<ImageDTO> imageDTOS =
                images.stream()
                        .map(i -> modelMapper.map(i, ImageDTO.class)).toList();
        cartItemDTO.getItem().setImages(imageDTOS);
        return cartItemDTO;

    }

    @Override
    public List<CartItem> findByCartId(Long cartId) {
        return cartItemRepository.findByCartId(cartId);
    }

    @Override
    public List<CartItemDTO> getCartItemsByUser(User user) {
        Cart cart = cartService.getCartByUserId(user.getId()); // Взимаме количката на потребителя
        if (cart == null) {
            throw new ResourceNotFoundException("No cart found for user " + user.getId());
        }

        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId()); // Взимаме артикулите в количката
        return cartItems.stream().map(this::convertToDTO).toList(); // Преобразуваме в DTO
    }


}
