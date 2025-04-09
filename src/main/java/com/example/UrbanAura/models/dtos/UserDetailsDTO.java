package com.example.UrbanAura.models.dtos;

import lombok.Data;

import java.util.List;

@Data
public class UserDetailsDTO {

    public Long id;
    public String firstName;
    public String lastName;
    public String email;
    public String address;
    public List<OrderDTO> orders;
    public List<String> roles;
    public CartDTO cart;

}
