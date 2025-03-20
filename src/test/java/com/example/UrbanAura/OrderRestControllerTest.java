//package com.example.UrbanAura;
//
//import com.example.UrbanAura.models.dtos.OrderDTO;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.example.UrbanAura.controllers.OrderRestController;
//import com.example.UrbanAura.services.order.OrderService;
//import com.example.UrbanAura.services.payment.PaymentServiceImpl;
//import com.example.UrbanAura.services.user.UserService;
//import org.junit.jupiter.api.Test;
//import org.mockito.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//
//import java.util.Collections;
//import java.util.List;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.http.RequestEntity.get;
//import static org.springframework.http.ResponseEntity.status;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(OrderRestController.class)
//public class OrderRestControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Mock
//    private OrderService orderService;
//
//    @Mock
//    private UserService userService;
//
//    @Mock
//    private PaymentServiceImpl paymentService;
//
//    @InjectMocks
//    private OrderRestController orderRestController;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//
//    @Test
//    public void testGetUserOrders_Success() throws Exception {
//        Long userId = 1L;  // Примерен потребителски ID
//        OrderDTO orderDTO = new OrderDTO();
//        orderDTO.setId(1L);  // Примерна поръчка
//        List<OrderDTO> orders = Collections.singletonList(orderDTO);  // Списък с една поръчка
//
//        // Мокваме метода getUserOrders да връща списък с поръчки
//        when(orderService.getUserOrders(userId)).thenReturn(orders);
//
//        mockMvc.perform(get("/api/v1/orders/{userId}/order", userId))
//                .andExpect(status().isOk())  // Очакваме статус код 200 OK
//                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Item Order Success!"))  // Проверка за съобщението
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").value(1));
//
//
//    }
//}
