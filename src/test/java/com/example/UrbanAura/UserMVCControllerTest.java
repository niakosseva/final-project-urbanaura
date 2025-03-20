package com.example.UrbanAura;

import com.example.UrbanAura.controllers.mvc.UserMVCController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(UserMVCController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserMVCControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testViewLoginForm () throws Exception {
        mvc.perform(get("/user/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void testRegistrationFom () throws Exception {
        mvc.perform(get("/user/account-register"))
                .andExpect(status().isOk())
                .andExpect(view().name("account-register"));
    }

    @Test
    public void testUserProfile () throws Exception {
        mvc.perform(get("/user/user-profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("user-profile"));
    }
}
