package com.example.UrbanAura.controllers.mvc;


import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserMVCController {


    @GetMapping("/login")
    public String viewLoginForm() {
        return "login";
    }

    @GetMapping("/account-register")
    public String showRegistrationForm() {
        return "account-register";
    }

    @GetMapping("/user-profile")
    public String showUserProfileForm() {
        return "user-profile";
    }


}
