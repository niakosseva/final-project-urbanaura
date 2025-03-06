package com.example.UrbanAura.controllers.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/my-order")
public class OrderController {


    @GetMapping("/checkout")
    public String viewCheckoutPage() {
        return "checkout";
    }
}
