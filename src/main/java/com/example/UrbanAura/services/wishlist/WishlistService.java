package com.example.UrbanAura.services.wishlist;

import com.example.UrbanAura.models.dtos.ItemDTO;

import java.util.List;

public interface WishlistService {
    List<ItemDTO> getWishlistItems(Long userId);
}
