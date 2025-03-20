package com.example.UrbanAura.repositories;

import com.example.UrbanAura.models.dtos.OrderDTO;
import com.example.UrbanAura.models.entities.Order;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);

    List<Order> findByOrderDateBefore(LocalDate thresholdDate);
}
