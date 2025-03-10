package com.example.UrbanAura.controllers.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admins")
public class AdminController {


    @GetMapping("admin")
    public String admin() {
        return "admin";
    }
}
