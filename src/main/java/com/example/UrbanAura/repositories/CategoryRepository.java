package com.example.UrbanAura.repositories;

import com.example.UrbanAura.models.entities.Category;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {


    Category findByName(String name);

    boolean existsByNameAndSlug(String name, String slug);

    Optional<Category> findBySlug(String slug);
}
