package com.example.UrbanAura.models.dtos;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class OrderItemDTO {
    private Long itemId;
    private String itemName;
    private int quantity;
    private BigDecimal price;
}
