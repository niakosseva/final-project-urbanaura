package com.example.UrbanAura.controllers;
import com.example.UrbanAura.models.dtos.CartDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalAdviceController {


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        String message = "Access Denied. You do not have permission for this action.";
        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);

    }



}
