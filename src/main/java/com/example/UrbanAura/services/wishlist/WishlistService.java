package com.example.UrbanAura.services.wishlist;

import com.example.UrbanAura.models.dtos.ItemDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface WishlistService {
//    List<ItemDTO> getWishlistItems(Long userId);

//    List<ItemDTO> getWishlistItemsForUser(Long userId);

    List<ItemDTO> getWishlistItemsForUser(Authentication authentication);
}
