package com.example.UrbanAura.services.exCurrency;


import com.example.UrbanAura.models.ExchangeRateResponse;

public interface CurrencyService {
     ExchangeRateResponse getExchangeRates(String baseCurrency);


//    Map<String, Double> listQuotes(String baseCurrency) throws IOException;
//   Double getExchangeRate(String from, String to, Double amount) throws IOException;

}
