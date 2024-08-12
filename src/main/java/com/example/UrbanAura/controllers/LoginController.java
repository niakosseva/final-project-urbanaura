package com.example.UrbanAura.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class LoginController {


    @GetMapping("/login")
    public String viewLoginForm(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password.");
        }
        return "login";
    }



//    @ModelAttribute("loginDTO")
//    public UserLoginDTO loginDTO() {
//        return new UserLoginDTO();\\
//    }

//    @GetMapping("/login")
//    public String viewLoginForm(@RequestParam(value = "error", required = false) String error,
//                                @RequestParam(value = "logout", required = false) String logout,
//                                Model model) {
//        if (error != null) {
//            model.addAttribute("loginError", "Invalid username or password.");
//        }
//        if (logout != null) {
//            model.addAttribute("logoutMessage", "You have been logged out successfully.");
//        }
//        return "login";
//    }


//    @GetMapping("/login")
//    public String viewLoginForm() {
//        return "login";
//
//    }
    //        //TODO fix this
//    @PostMapping("/login-error")
//    public String viewLoginError(@RequestParam ("username") String username,
//                                     RedirectAttributes redirectAttributes) {
//        redirectAttributes.addFlashAttribute("username", username);
//        redirectAttributes.addFlashAttribute("error",
//                "Invalid email or password.");
//        return "redirect:/login";
//    }
}







