package com.example.UrbanAura.services.impl;


import com.example.UrbanAura.exceptions.ResourceNotFoundException;
import com.example.UrbanAura.models.entities.Item;
import com.example.UrbanAura.repositories.ItemRepository;
import com.example.UrbanAura.services.ItemService;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Iterable<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public Item getItem(long id) {
        return itemRepository
                .findById(id)
                .orElseThrow(() ->new ResourceNotFoundException("Item not found!"));
    }

    @Override
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }
}
