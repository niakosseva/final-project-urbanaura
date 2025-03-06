package com.example.UrbanAura.services.order;

import com.example.UrbanAura.exceptions.ResourceNotFoundException;
import com.example.UrbanAura.models.dtos.OrderDTO;
import com.example.UrbanAura.models.dtos.PaymentRequest;
import com.example.UrbanAura.models.entities.*;
import com.example.UrbanAura.models.enums.OrderStatus;
import com.example.UrbanAura.repositories.ItemRepository;
import com.example.UrbanAura.repositories.OrderRepository;
import com.example.UrbanAura.services.cart.CartService;
import com.example.UrbanAura.services.user.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final CartService cartService;
    private final ModelMapper modelMapper;
    private final UserServiceImpl userServiceImpl;

    public OrderServiceImpl(OrderRepository orderRepository, ItemRepository itemRepository, CartService cartService, ModelMapper modelMapper, UserServiceImpl userServiceImpl) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.cartService = cartService;
        this.modelMapper = modelMapper;
        this.userServiceImpl = userServiceImpl;
    }

    @Transactional
    @Override
    public Order placeOrder(Long userId) {
        User user = userServiceImpl.getAuthenticatedUser();
        Cart cart = cartService.getCartByUserId(user.getId());
        Order order = createOrder(cart);
        List<OrderItem> orderItemList = createOrderItems(order, cart);

        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalAmount(calculateTotalAmount(orderItemList));

        //saving the order we just got to the DB
        Order saveOrder = orderRepository.save(order);

        cartService.clearCart(cart.getId());


        return saveOrder;
    }

    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return cart.getItems()
                .stream()
                .map(cartItem -> {
                    Item item = cartItem.getItem();
                    item.setInventory(item.getInventory() - cartItem.getQuantity());
                    itemRepository.save(item);
                    return new OrderItem(
                            order,
                            item,
                            cartItem.getQuantity(),
                            cartItem.getUnitPrice()

                    );
                }).toList();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItems) {
        return orderItems.stream().map(item -> item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);


    }

    @Override
    public OrderDTO getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Customer Order not found"));
    }

    @Override
    public List<OrderDTO> getUserOrders(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(this::convertToDTO).toList();


    }

    @Override
    public OrderDTO convertToDTO(Order order) {
        return modelMapper.map(order, OrderDTO.class);
    }



}
