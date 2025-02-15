package com.example.UrbanAura.controllers.mvc;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

    @GetMapping("/shopping-cart")
    public String viewShoppingCart() {
        return "shopping-cart";

    }
}
