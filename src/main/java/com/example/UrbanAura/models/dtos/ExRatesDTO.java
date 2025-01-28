package com.example.UrbanAura.models.dtos;



import java.math.BigDecimal;
import java.util.Map;


public record ExRatesDTO(String base, Map<String, BigDecimal> rates) {


    @Override
    public String base() {
        return base;
    }

    @Override
    public Map<String, BigDecimal> rates() {
        return rates;
    }
}



