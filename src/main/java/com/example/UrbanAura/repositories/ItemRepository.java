package com.example.UrbanAura.repositories;

import com.example.UrbanAura.models.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByCategoryName(String category);

    Long countByName(String name);

    List<Item> findByName(String name);

    boolean existsByName(String name);
}
