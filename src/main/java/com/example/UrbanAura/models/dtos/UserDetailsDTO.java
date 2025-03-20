package com.example.UrbanAura.models.dtos;

import com.example.UrbanAura.models.entities.Role;
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
    private List<String> roles;
    private CartDTO cart;


}
