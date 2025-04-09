package com.example.UrbanAura.controllers.mvc;

import com.example.UrbanAura.models.dtos.ItemDTO;
import com.example.UrbanAura.models.entities.User;
import com.example.UrbanAura.services.user.UserService;
import com.example.UrbanAura.services.wishlist.WishlistService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("/wishlist")
    public String getWishlist(Model model, Authentication authentication) {
        if (authentication == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not logged in");
        }
        List<ItemDTO> wishlistItems = wishlistService.getWishlistItemsForUser(authentication);
        model.addAttribute("wishlistItems", wishlistItems);

        return "wishlist";
    }

}

