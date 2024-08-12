package com.example.UrbanAura.controllers;



import com.example.UrbanAura.services.AppUserDetailsService;
import com.example.UrbanAura.user.UrbanAuraUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        if (userDetails instanceof UrbanAuraUserDetails urbanAuraUserDetails) {
            model.addAttribute("currentUsername", urbanAuraUserDetails.getUsername());
            System.out.println("User with username " + urbanAuraUserDetails.getUsername() + "is logged in.");
        }
//        else {
//            model.addAttribute("currentUsername", null);
//        }

        return "index";
    }


    @GetMapping("/shopping-cart")
    public String shoppingCart() {

        return "shopping-cart";
    }



    @GetMapping("/home-03")
    public String home3() {
        return "home-03";
    }


}
