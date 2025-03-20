package com.example.UrbanAura.scheduleComponent;

import com.example.UrbanAura.models.entities.Order;
import com.example.UrbanAura.models.enums.OrderStatus;
import com.example.UrbanAura.repositories.CartItemRepository;
import com.example.UrbanAura.repositories.OrderRepository;
import com.example.UrbanAura.services.cartItem.CartItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ScheduleTasks {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleTasks.class);


    private final OrderRepository orderRepository;

    public ScheduleTasks(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Scheduled(fixedDelay = 7776000000L)
    public void archiveOldOrders() {
        logger.info("Running Order Archiving Task...");

        // Определяме праг – поръчките, чиито orderDate е преди 90 дни от сега, ще бъдат архивирани.
        LocalDate thresholdDate = LocalDate.now().minusDays(90);
        List<Order> oldOrders = orderRepository.findByOrderDateBefore(thresholdDate);

        for (Order order : oldOrders) {
            // Пример: обновяваме статуса на поръчката на ARCHIVED
            order.setOrderStatus(OrderStatus.ARCHIVED);
            orderRepository.save(order);
            logger.info("Archived order with ID: {}", order.getId());
        }

        logger.info("Order Archiving Task completed. Total orders archived: {}", oldOrders.size());
    }

}
