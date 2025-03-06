package com.example.UrbanAura.controllers.mvc;

import com.example.UrbanAura.models.dtos.ItemDTO;
import com.example.UrbanAura.models.entities.User;
import com.example.UrbanAura.services.user.UserService;
import com.example.UrbanAura.services.wishlist.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WishlistController {

    private final WishlistService wishlistService;
    private final UserService userService;

    public WishlistController(WishlistService wishlistService, UserService userService) {
        this.wishlistService = wishlistService;
        this.userService = userService;
    }

    @GetMapping("/wishlist")
    public String getWishlist(Model model) {
        User user = userService.getAuthenticatedUser();
        List<ItemDTO> wishlistItems = wishlistService.getWishlistItems(user.getId());
        model.addAttribute("wishlistItems", wishlistItems);
        return "wishlist";
    }

}

