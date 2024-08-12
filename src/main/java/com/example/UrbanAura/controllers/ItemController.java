package com.example.UrbanAura.controllers;


import com.example.UrbanAura.models.entities.Item;
import com.example.UrbanAura.services.impl.ItemServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/items")
public class ItemController {

    private final ItemServiceImpl itemService;

    public ItemController(ItemServiceImpl itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/product-detail")
    public String getAllItems(Model model) {
        Iterable<Item> items = itemService.getAllItems();
        model.addAttribute("items", items);
        return "product-detail";

    }
}
