package com.example.UrbanAura.services.wishlist;

import com.example.UrbanAura.models.dtos.ItemDTO;
import com.example.UrbanAura.models.entities.Item;
import com.example.UrbanAura.services.item.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class WishlistServiceImpl implements WishlistService {

    private final RestTemplate restTemplate;
    private final ItemService itemService; // Свързано със съществуващите продукти

    public WishlistServiceImpl(RestTemplate restTemplate, ItemService itemService) {
        this.restTemplate = restTemplate;
        this.itemService = itemService;
    }

    @Override
    public List<ItemDTO> getWishlistItems(Long userId) {
        String wishlistUrl = "http://localhost:8082/api/v1/wishlist/" + userId;
        ResponseEntity<Long[]> response = restTemplate.getForEntity(wishlistUrl, Long[].class);

        if (response.getBody() == null) {
            return new ArrayList<>();
        }

        Long[] itemIds = response.getBody();
        Set<ItemDTO> items = new HashSet<>();
        for (Long itemId : itemIds) {
            Item item = itemService.getItemById(itemId);
            if (item != null) {
                ItemDTO itemDTO = itemService.convertToDto(item);
                items.add((itemDTO));
            }
        }
        return new ArrayList<>(items);
    }
}
