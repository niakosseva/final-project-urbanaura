package com.example.UrbanAura.models.dtos;

import java.math.BigDecimal;

public record ConversionResultDTO (String from, String to, BigDecimal amount, BigDecimal result) {

}
