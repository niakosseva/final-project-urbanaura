package com.example.UrbanAura.models.dtos;

import lombok.Data;

@Data
public class PaymentRequest {
    public String cardName;
    public String cardNumber;
    public String expMonth;
    public String expYear;
    public String cvv;
}
