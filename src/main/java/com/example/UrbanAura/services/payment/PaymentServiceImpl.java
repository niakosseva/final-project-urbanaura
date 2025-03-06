package com.example.UrbanAura.services.payment;

import com.example.UrbanAura.models.dtos.PaymentRequest;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl {

    private boolean validatePayment(PaymentRequest payment) {
        return payment.getCardNumber() != null && payment.getCardNumber().length() == 16;
    }

    public boolean processPayment(PaymentRequest payment) {
        if (!validatePayment(payment)) {
            return false; // Неуспешно плащане
        }
        return true; // Успешно плащане (симулирано)
    }
}
