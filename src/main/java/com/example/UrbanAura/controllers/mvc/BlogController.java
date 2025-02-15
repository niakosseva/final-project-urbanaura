package com.example.UrbanAura.controllers.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BlogController {


    @GetMapping("/blog")
    public String blog() {
        return "blog";
    }
}
