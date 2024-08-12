package com.example.UrbanAura.services;

import com.example.UrbanAura.models.entities.Item;

public interface ItemService {

    Iterable<Item> getAllItems();
    Item getItem(long id);
    Item saveItem(Item item);
}
