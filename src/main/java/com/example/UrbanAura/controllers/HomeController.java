package com.example.UrbanAura.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {


    @GetMapping("/")
    public String index() {
        return "index";
    }



    @GetMapping("/home-03")
    public String home() {
        return "home-03";
    }


}
