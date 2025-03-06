package com.example.UrbanAura.controllers.mvc;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ShoppingCartController {

    @GetMapping("/shopping-cart")
    public String viewShoppingCart() {
        return "shopping-cart";

    }
}
