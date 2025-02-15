package com.example.UrbanAura.controllers.mvc;

import com.example.UrbanAura.exceptions.ResourceNotFoundException;
import com.example.UrbanAura.models.dtos.ItemDTO;
import com.example.UrbanAura.models.entities.Item;
import com.example.UrbanAura.services.item.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/item")
public class ItemDetailController {
    private final ItemService itemService;

    public ItemDetailController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/{slug}")
    public String showItemDetail(@PathVariable String slug, Model model) {
        Item item = itemService.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Uh oh! Item not found"));
        ItemDTO itemDTO = itemService.convertToDto(item);
        model.addAttribute("item", itemDTO);
        return "item-detail";

    }
}
