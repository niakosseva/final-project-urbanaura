//package com.example.UrbanAura;
//
//import com.example.UrbanAura.requests.LoginRequest;
//import com.example.UrbanAura.user.jwt.JwtUtils;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.http.Cookie;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.when;
//import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.get;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class AuthRestControllerTest {
//
//    @Autowired
//    private MockMvc mvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private AuthenticationManager authenticationManager;
//
//    @MockBean
//    private JwtUtils jwtUtils;
//
//
//    private final LoginRequest loginRequest = new LoginRequest();
//
//    @AfterEach
//    public void cleanup() {
//        SecurityContextHolder.clearContext(); // Нулиране на контекста на сигурността след всеки тест
//    }
//
//    @BeforeEach
//    public void setup() {
//        loginRequest.setEmail("test@example.com");
//        loginRequest.setPassword("password");
//    }
//    @Test
//    public void testJwtGeneration() {
//        String jwt = jwtUtils.generateTokenForUser(new UsernamePasswordAuthenticationToken("test@example.com", "password"));
//        assertNotNull(jwt);  // Проверка дали токенът не е null
//        assertTrue(jwt.startsWith("eyJ"));  // Проверка дали токенът започва с "eyJ" (типично за JWT)
//    }
//
//    @Test
//    public void testUserStatusWithJwt() throws Exception {
//
//        String jwt = "testJwtToken";
//        when(jwtUtils.validateToken(jwt)).thenReturn(true);
//        when(jwtUtils.getFirstNameFromToken(jwt)).thenReturn("John");
//        mvc.perform(MockMvcRequestBuilders.get("/api/v1/auth/user/status")
//                        .cookie(new org.springframework.mock.web.MockCookie("jwt", jwt))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.authenticated").value(true))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"));
//    }
//
//}
