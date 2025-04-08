package com.example.UrbanAura.services.wishlist;

import com.example.UrbanAura.models.dtos.ItemDTO;
import com.example.UrbanAura.models.entities.Item;
import com.example.UrbanAura.services.item.ItemService;
import com.example.UrbanAura.user.jwt.JwtUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class WishlistServiceImpl implements WishlistService {

    private final RestTemplate restTemplate;
    private final ItemService itemService;
    private final JwtUtils jwtUtils;

    public WishlistServiceImpl(RestTemplate restTemplate, ItemService itemService, JwtUtils jwtUtils) {
        this.restTemplate = restTemplate;
        this.itemService = itemService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public List<ItemDTO> getWishlistItemsForUser(Authentication authentication) {
        String wishlistUrl = "http://localhost:8082/api/v1/wishlist/user/wishlist";

        String token = jwtUtils.generateTokenForUser(authentication);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token); // Adding the token here

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Long[]> response;
        try {
            response = restTemplate.exchange(wishlistUrl, HttpMethod.GET, entity, Long[].class);
        } catch (Exception e) {
            return new ArrayList<>();
        }

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
//HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", authHeader);
//HttpEntity<String> entity = new HttpEntity<>(headers);
//        try {
//ResponseEntity<ApiResponse> response = restTemplate.exchange(wishlistUrl, HttpMethod.GET, entity, ApiResponse.class);
//
//            if (response.getBody() == null || !response.getBody().getMessage().equals("success")) {
//        return new ArrayList<>();
//        }
//
//List<Long> itemIds = (List<Long>) response.getBody().getData();
//Set<ItemDTO> items = new HashSet<>();
//            for (Long itemId : itemIds) {
//Item item = itemService.getItemById(itemId);
//                if (item != null) {
//ItemDTO itemDTO = itemService.convertToDto(item);
//                    items.add((itemDTO));
//        }
//        }
//        return new ArrayList<>(items);
//        } catch (RestClientException e) {
//        throw new RuntimeException(e);
//        }