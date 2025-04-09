package com.example.UrbanAura.controllers;

import com.example.UrbanAura.exceptions.ResourceNotFoundException;
import com.example.UrbanAura.models.dtos.OrderDTO;
import com.example.UrbanAura.models.dtos.PaymentRequest;
import com.example.UrbanAura.models.entities.Order;
import com.example.UrbanAura.models.entities.User;
import com.example.UrbanAura.response.ApiResponse;
import com.example.UrbanAura.services.order.OrderService;
import com.example.UrbanAura.services.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderRestController {

    private final OrderService orderService;
    private final UserService userService;


    public OrderRestController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder(@RequestBody PaymentRequest paymentRequest) {
        try {
            boolean validationNotNullResult = orderService.validatePaymentRequest(paymentRequest);
            boolean validationDigitsResult = orderService.validateCardCredentialsRequest(paymentRequest);
            if (!validationNotNullResult) {
                return ResponseEntity.status(BAD_REQUEST)
                        .body(new ApiResponse("All fields are required!", null));
            }
            if (!validationDigitsResult) {
                return ResponseEntity.status(BAD_REQUEST)
                        .body(new ApiResponse("Invalid numbers in card details!", null));
            }
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
