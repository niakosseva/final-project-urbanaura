package com.example.UrbanAura.controllers;

import com.example.UrbanAura.exceptions.ResourceNotFoundException;
import com.example.UrbanAura.models.dtos.CartItemDTO;
import com.example.UrbanAura.models.dtos.CartItemRequest;
import com.example.UrbanAura.models.entities.Cart;
import com.example.UrbanAura.models.entities.User;
import com.example.UrbanAura.response.ApiResponse;
import com.example.UrbanAura.services.cart.CartService;
import com.example.UrbanAura.services.cartItem.CartItemService;
import com.example.UrbanAura.services.user.UserService;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemRestController {
    private final CartItemService cartItemService;
    private final CartService cartService;
    private final UserService userService;

    public CartItemRestController(CartItemService cartItemService, CartService cartService, UserService userService) {
        this.cartItemService = cartItemService;
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping("/my-cart-items")
    public ResponseEntity<ApiResponse> getCartItems() {
        try {
            User user = userService.getAuthenticatedUser();
            List<CartItemDTO> cartItems = cartItemService.getCartItemsByUser(user);

            return ResponseEntity.ok(new ApiResponse("Cart Items Loaded", cartItems));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error loading cart items", null));
        }
    }


    @GetMapping("/count")
    public ResponseEntity<?> getCartItemCount() {
        User user = userService.getAuthenticatedUser();
        Cart cart = cartService.getCartByUserId(user.getId());
        int itemCount = cartItemService.getCartItemCount(cart.getId());

        return ResponseEntity.ok(itemCount);
    }


    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(
            @RequestBody CartItemRequest request) {
        try {
            User user = userService.getAuthenticatedUser();
            Cart cart = cartService.initializeNewCart(user);
            cartItemService.addItemToCart(cart.getId(), request.getItemId(), request.getQuantity());
            return ResponseEntity.ok(new ApiResponse("Added Item Successfully!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        } catch (JwtException e) {
            return ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));

        }
    }

    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId,
                                                          @PathVariable Long itemId) {
        try {
            cartItemService.removeItemFromCart(cartId, itemId);
            return ResponseEntity.ok(new ApiResponse("Removed Items Successfully!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId,
                                                          @PathVariable Long itemId,
                                                          @RequestParam Integer quantity) {
        try {
            cartItemService.updateItemQuantity(cartId, itemId, quantity);
            return ResponseEntity.ok(new ApiResponse("Updated Item Successfully!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }


    }
}
