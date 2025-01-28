package com.example.UrbanAura.controllers;


import com.example.UrbanAura.services.user.UserService;
import com.example.UrbanAura.user.UrbanAuraUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/index")
    public String index( Model model) {

        return "index";
    }

    @GetMapping("/home-03")
    public String home3() {
        return "home-03";
    }


}
//    @GetMapping("/header")
//    public String header(@AuthenticationPrincipal UserDetails userDetails, Model model){
//        if (userDetails instanceof UrbanAuraUserDetails urbanAuraUserDetails) {
//            model.addAttribute("currentUsername", urbanAuraUserDetails.getUsername());
//        } else {
//            model.addAttribute("currentUsername", null);
//        }
//        return "/fragments/header";
//    }


//
//    @PostMapping("/index")
//    public String addItemToCart(@ModelAttribute AddItemToCartDTO addItemToCartDTO,RedirectAttributes redirectAttributes) {
//        if (addItemToCartDTO.getItemId() == null) {
//            System.err.println("Error: Item ID is null.");
//            redirectAttributes.addFlashAttribute("error", "Failed to add item to cart: Item ID is missing.");
//            return "redirect:/index";
//        }
//
//        if (addItemToCartDTO.getQuantity() == null) {
//            System.err.println("Warning: Quantity is null. Setting default value.");
//            addItemToCartDTO.setQuantity(1); // Default quantity value
//        }
//
//        System.out.println("Received itemId: " + addItemToCartDTO.getItemId());
//        System.out.println("Received quantity: " + addItemToCartDTO.getQuantity());
//        System.out.println("Received size: " + addItemToCartDTO.getSize());
//
//        cartService.addItemToCart(addItemToCartDTO);
//
//        redirectAttributes.addFlashAttribute("message", "Item successfully added to the cart.");
//
//        return "redirect:/index";
//    }

