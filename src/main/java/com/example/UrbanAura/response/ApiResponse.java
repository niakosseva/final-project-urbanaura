package com.example.UrbanAura.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ApiResponse {

    private String message;
    private Object data;
}
