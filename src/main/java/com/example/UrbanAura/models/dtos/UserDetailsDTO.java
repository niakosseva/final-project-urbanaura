package com.example.UrbanAura.models.dtos;

import lombok.Data;

import java.util.List;

@Data
public class UserDetailsDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private List<OrderDTO> orders;
    private CartDTO cart;


}
