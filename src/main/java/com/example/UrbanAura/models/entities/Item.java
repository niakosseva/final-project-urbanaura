package com.example.UrbanAura.models.entities;

import com.example.UrbanAura.models.BaseEntity.BaseEntity;

import jakarta.persistence.*;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Item extends BaseEntity {


    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int inventory;


    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "item" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;

    @Column(nullable = false, unique = true)
    private String slug;


    public Item(String name, BigDecimal price, int inventory, String description, Category category) {
        this.name = name;
        this.price = price;
        this.inventory = inventory;
        this.description = description;
        this.category = category;
    }
}
