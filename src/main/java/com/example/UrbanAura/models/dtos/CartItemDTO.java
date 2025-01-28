package com.example.UrbanAura.models.dtos;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class CartItemDTO {

    private Long itemId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private ItemDTO item;

}
