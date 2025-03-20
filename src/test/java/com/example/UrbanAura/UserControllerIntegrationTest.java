package com.example.UrbanAura;

import com.example.UrbanAura.controllers.UserController;
import com.example.UrbanAura.models.dtos.UserDetailsDTO;
import com.example.UrbanAura.models.entities.User;
import com.example.UrbanAura.services.user.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserServiceImpl userService;



    @Test
    void testGetUserById_Success() throws Exception {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");

        UserDetailsDTO userDto = new UserDetailsDTO();
        userDto.setId(userId);
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("john.doe@example.com");

        Mockito.when(userService.getUserById(userId)).thenReturn(user);
        Mockito.when(userService.convertUserToDto(user)).thenReturn(userDto);

        mockMvc.perform(get("/api/v1/users/{userId}/user", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.firstName").value("John"))
                .andExpect(jsonPath("$.data.lastName").value("Doe"))
                .andExpect(jsonPath("$.data.email").value("john.doe@example.com"));

    }
    @Test
   void testGetUserById_Failure() throws Exception {

    }

}
