package com.example.UrbanAura.controllers;

import com.example.UrbanAura.models.dtos.UserLoginDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class LoginController {
    @ModelAttribute("loginDTO")
    public UserLoginDTO loginDTO() {
        return new UserLoginDTO();
    }

    @GetMapping("/login")
    public String viewLoginForm() {
        return "login";

    }

    @PostMapping("/login-error")
    public String viewLoginError(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("loginError",
                "Invalid email or password.");
        return "redirect:/login";
    }
}


//