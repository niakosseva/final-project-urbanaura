package com.example.UrbanAura.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            model.addAttribute("message", "Please try again later.");


            if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
                return "401"; //
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "403"; //
            } else if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "404"; //
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "500"; //
            }
        }

        return "error";
    }

}

