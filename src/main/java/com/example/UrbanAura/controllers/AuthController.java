package com.example.UrbanAura.controllers;


import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class AuthController {


    @GetMapping("/login")
    public String viewLoginForm() {
       return "login";
    }


}
