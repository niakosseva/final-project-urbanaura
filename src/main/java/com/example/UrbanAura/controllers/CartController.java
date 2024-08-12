package com.example.UrbanAura.controllers;

import com.example.UrbanAura.models.entities.CartItem;
import com.example.UrbanAura.services.impl.CartServiceImpl;
import com.example.UrbanAura.user.UrbanAuraUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartServiceImpl cartService;

    public CartController(CartServiceImpl cartService) {
        this.cartService = cartService;
    }



    @PostMapping("/add")
    public String addItemToCart(@RequestParam("itemId") Long itemId,
                                @RequestParam("quantity") int quantity) {
        Long userId = getLoggedInUserId();
        cartService.addItemToCart(userId, itemId, quantity);
        return "redirect:/cart";


    }

    @PostMapping("/remove")
    public String removeItemFromCart(@RequestParam("itemId") Long itemId) {
        Long userId = getLoggedInUserId();
        cartService.removeItemFromCart(userId, itemId);
        return "redirect:/cart";
    }

    @GetMapping
    public String getCart(Model model) {
        Long userId = getLoggedInUserId();
        if (userId != null) {
            Set<CartItem> cartItems = cartService.getCartItems(userId);
            model.addAttribute("cartItems", cartItems);
            return "shopping-cart";
        }
        return "redirect:/login";
    }


    private Long getLoggedInUserId() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {

            return ((UrbanAuraUserDetails) userDetails).getId();
        }
        //TODO throw an exception?
        return null;
    }
}
