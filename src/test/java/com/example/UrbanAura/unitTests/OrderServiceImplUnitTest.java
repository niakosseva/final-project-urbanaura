package com.example.UrbanAura.unitTests;

import com.example.UrbanAura.models.entities.*;
import com.example.UrbanAura.models.enums.OrderStatus;
import com.example.UrbanAura.repositories.ItemRepository;
import com.example.UrbanAura.repositories.OrderRepository;
import com.example.UrbanAura.services.cart.CartService;
import com.example.UrbanAura.services.order.OrderServiceImpl;
import com.example.UrbanAura.services.user.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplUnitTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private CartService cartService;
    @Mock
    private UserServiceImpl userServiceImpl;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private OrderServiceImpl orderService;


    @Test
    void testPlaceOrder_Success() {
        User user = new User();
        user.setId(1L);

        // Създаване на Item и CartItem
        Item item = new Item();
        item.setId(1L);
        item.setName("Test Item");
        item.setPrice(BigDecimal.valueOf(100.00));

        CartItem cartItem = new CartItem();
        cartItem.setItem(item);  // Set the item for CartItem
        cartItem.setQuantity(1);
        cartItem.setUnitPrice(item.getPrice());

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setItems(Collections.singleton(cartItem));

        when(userServiceImpl.getAuthenticatedUser()).thenReturn(user);
        when(cartService.getCartByUserId(user.getId())).thenReturn(cart);

        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            order.setOrderStatus(OrderStatus.PENDING);
            return order;
        });

        when(itemRepository.save(any(Item.class))).thenReturn(item);

        Order order = orderService.placeOrder(user.getId());

        assertNotNull(order);
        assertEquals(OrderStatus.PENDING, order.getOrderStatus());
        verify(orderRepository).save(any(Order.class));
        verify(itemRepository).save(any(Item.class));
    }


}
