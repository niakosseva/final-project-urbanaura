package com.example.UrbanAura.services.item;

import com.example.UrbanAura.models.dtos.ItemDTO;
import com.example.UrbanAura.requests.AddItemRequest;
import com.example.UrbanAura.requests.ItemUpdateRequest;
import com.example.UrbanAura.models.entities.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    Item addItem(AddItemRequest request);
    Item getItemById(Long id);
    List<Item> getItemsByName(String name);
    void deleteItemById(Long id);
    Item updateItem(ItemUpdateRequest item, Long itemId);
    List<Item> getAllItems();
    List<Item> findItemsByCategory(String category);


    List<ItemDTO> getConvertedItems(List<Item> items);

    ItemDTO convertToDto(Item item);

    Long countItemsByName(String name);

    Optional<Item> findBySlug(String slug);

    String generateSlug(String name);
}
