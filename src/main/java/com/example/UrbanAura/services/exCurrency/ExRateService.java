package com.example.UrbanAura.services.exCurrency;

import com.example.UrbanAura.models.dtos.ExRatesDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ExRateService {

    boolean hasInitializedRates();

    ExRatesDTO fetchRates();

    void updateRates(ExRatesDTO exRatesDTO);

    Optional<BigDecimal> findExRate(String from, String to);

    BigDecimal convert (String from, String to, BigDecimal amount);

    List<String> allSupportedCurrencies();
}
