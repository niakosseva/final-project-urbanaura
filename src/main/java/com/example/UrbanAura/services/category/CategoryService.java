package com.example.UrbanAura.services.category;

import com.example.UrbanAura.models.entities.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category, Long id);
    void deleteCategoryById(Long id);

    Optional<Category>getCategoryBySlug(String slug);
}
