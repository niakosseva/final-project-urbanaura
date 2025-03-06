package com.example.UrbanAura.models.dtos;

import lombok.Data;

@Data
public class CartItemRequest {

    private Long itemId;
    private Integer quantity;
}
