package com.example.UrbanAura.controllers;

import com.example.UrbanAura.models.dtos.OrderDTO;
import com.example.UrbanAura.models.dtos.UserDetailsDTO;
import com.example.UrbanAura.models.entities.User;
import com.example.UrbanAura.repositories.UserRepository;
import com.example.UrbanAura.services.admin.AdminService;
import com.example.UrbanAura.services.order.OrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admins")
public class AdminRestController {

    private final AdminService adminService;
    private final OrderService orderService;

    public AdminRestController(AdminService adminService, UserRepository userRepository, OrderService orderService) {
        this.adminService = adminService;
        this.orderService = orderService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/users/changeRoles")
    public String changeUserRole(@RequestParam String email, @RequestParam String newRole) {
        adminService.changeRoles(email, newRole);
        return "redirect:/admins/admin";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String getAllUser(Model model) {
        List<User> users = adminService.getAllUsers();
        List<UserDetailsDTO> convertedUser = adminService.getConvertedUsers(users);
        model.addAttribute("convertedUser", convertedUser);
        return "admin";

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("user-orders")
    public String getUserOrders(Model model) {
        List<OrderDTO> orders = orderService.getAllOrders();
        System.out.println("Orders found: " + orders.size());
        model.addAttribute("orders", orders);
        return "user-orders";

    }


}
