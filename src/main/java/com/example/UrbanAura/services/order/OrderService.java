package com.example.UrbanAura.services.order;

import com.example.UrbanAura.models.dtos.OrderDTO;
import com.example.UrbanAura.models.dtos.PaymentRequest;
import com.example.UrbanAura.models.entities.Order;

import java.util.List;

public interface OrderService {

    Order placeOrder(Long userId);

    OrderDTO getOrder(Long orderId);

    List<OrderDTO> getUserOrders(Long userId);

    OrderDTO convertToDTO(Order order);

    List<OrderDTO> getAllOrders();

    boolean validatePaymentRequest(PaymentRequest paymentRequest);

    boolean validateCardCredentialsRequest(PaymentRequest paymentRequest);
}
