package com.example.UrbanAura.controllers.mvc;

import com.example.UrbanAura.models.entities.Category;
import com.example.UrbanAura.models.entities.Item;
import com.example.UrbanAura.services.category.CategoryService;
import com.example.UrbanAura.services.item.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/clothing-categories")
public class ClothingCategoriesController {

    private final ItemService itemService;
    private final CategoryService categoryService;

    public ClothingCategoriesController(ItemService itemService, CategoryService categoryService) {
        this.itemService = itemService;
        this.categoryService = categoryService;
    }
    @GetMapping("/{slug}")
    public String showCategoryPage(@PathVariable String slug, Model model) {
        Category category = categoryService.getCategoryBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        List<Item> items = itemService.findItemsByCategory(category.getName());
        model.addAttribute( "items", items);
        model.addAttribute("category", category.getName());

        return "category";
    }

}
