package com.example.UrbanAura.controllers;

import com.example.UrbanAura.exceptions.ResourceNotFoundException;
import com.example.UrbanAura.models.dtos.OrderDTO;
import com.example.UrbanAura.models.dtos.PaymentRequest;
import com.example.UrbanAura.models.entities.Order;
import com.example.UrbanAura.models.entities.User;
import com.example.UrbanAura.response.ApiResponse;
import com.example.UrbanAura.services.order.OrderService;
import com.example.UrbanAura.services.payment.PaymentServiceImpl;
import com.example.UrbanAura.services.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderRestController {

    private final OrderService orderService;
    private final UserService userService;
    private final PaymentServiceImpl paymentService;


    public OrderRestController(OrderService orderService, UserService userService, PaymentServiceImpl paymentService) {
        this.orderService = orderService;
        this.userService = userService;
        this.paymentService = paymentService;
    }

    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder(@RequestBody PaymentRequest paymentRequest) {
        try {
//            if (!paymentService.processPayment(paymentRequest)) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                        .body(new ApiResponse("Payment failed. Please check your card details.", null));
//            }
            User user = userService.getAuthenticatedUser();
            Order order = orderService.placeOrder(user.getId());
            OrderDTO orderDTO = orderService.convertToDTO(order);
            return ResponseEntity.ok(new ApiResponse("Item Order Success!", orderDTO));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error occurred!", e.getMessage()));
        }

    }

    @GetMapping("{orderId}/order")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId) {
        try {
            OrderDTO order = orderService.getOrder(orderId);
            return ResponseEntity.ok(new ApiResponse("Item Order Success!", order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse("Oops!", e.getMessage()));
        }
    }


    @GetMapping("{userId}/order")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId) {
        try {
            List<OrderDTO> order = orderService.getUserOrders(userId);
            return ResponseEntity.ok(new ApiResponse("Item Order Success!", order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse("Oops!", e.getMessage()));
        }
    }
}
