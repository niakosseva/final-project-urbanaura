package com.example.UrbanAura.controllers;


import com.example.UrbanAura.exceptions.EmailAndUsernameAlreadyExistsException;
import com.example.UrbanAura.models.dtos.UserRegistrationDTO;
import com.example.UrbanAura.services.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("registerDTO")
    public UserRegistrationDTO registerDTO() {
        return new UserRegistrationDTO();
    }

    @GetMapping("/account-register")
    public String showRegistrationForm() {


        return "account-register";
    }

    @PostMapping("/account-register")
    public String doRegister(@Valid UserRegistrationDTO registerDTO, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            return "account-register";
        }


        try {
            userService.registerUser(registerDTO);
            return "redirect:/";
        } catch (EmailAndUsernameAlreadyExistsException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "redirect:/users/account-register";
        }


    }
}

