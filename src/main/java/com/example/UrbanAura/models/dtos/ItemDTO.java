package com.example.UrbanAura.models.dtos;


import com.example.UrbanAura.models.entities.Category;

import lombok.Data;


import java.math.BigDecimal;
import java.util.List;

@Data
public class ItemDTO {

    private Long id;
    private String name;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
    private List<ImageDTO> images;





}

