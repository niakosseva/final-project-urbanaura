package com.example.UrbanAura.controllers;

import com.example.UrbanAura.models.dtos.ItemDTO;
import com.example.UrbanAura.models.entities.Item;
import com.example.UrbanAura.services.item.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoriesController {

    private final ItemService itemService;

    public CategoriesController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/women")
    public String showWomenPage (Model model){
        System.out.println("Fetching items for Women category"); // Add this log
        List<Item> item = itemService.findItemsByCategory("Women");
        List<ItemDTO> itemDTO = itemService.getConvertedItems(item);

        model.addAttribute("items", itemDTO);

        return "women";
    }
}
