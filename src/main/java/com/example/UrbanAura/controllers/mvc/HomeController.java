package com.example.UrbanAura.controllers.mvc;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    @GetMapping("/index")
    public String index() {

        return "index";
    }

    @GetMapping("/home-03")
    public String home3() {
        return "home-03";
    }


}


