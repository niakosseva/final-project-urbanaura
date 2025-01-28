package com.example.UrbanAura.requests;

import com.example.UrbanAura.models.entities.Category;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class ItemUpdateRequest {
    private Long id;
    private String name;
    private BigDecimal price;
    private int inventory;
    private String description;

    private Category category;
}
