package com.example.UrbanAura.models.dtos;

import lombok.Data;

@Data
public class PaymentRequest {
    private String cardName;
    private String cardNumber;
    private String expMonth;
    private String expYear;
    private String cvv;
}
