package com.example.UrbanAura.integrationTests;

import com.example.UrbanAura.models.dtos.UserDetailsDTO;
import com.example.UrbanAura.models.dtos.UserUpdateUsernameDTO;
import com.example.UrbanAura.models.entities.User;
import com.example.UrbanAura.repositories.UserRepository;
import com.example.UrbanAura.requests.CreateUserRequest;
import com.example.UrbanAura.requests.UserUpdateRequest;
import com.example.UrbanAura.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
public class UserRestControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;


    @Test
    void testGetUserByIdIT_Success() throws Exception {
        long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");
        userRepository.save(user);

        ResponseEntity<ApiResponse> response =
                restTemplate.getForEntity("/api/v1/users/{userId}/user", ApiResponse.class, userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        ApiResponse body = response.getBody();
        assertNotNull(body);
        assertEquals("Success", body.getMessage());

        // Преобразуваме ръчно data към UserDetailsDTO, защото ApiResponse не е generic
        UserDetailsDTO userDto = objectMapper.convertValue(body.getData(), UserDetailsDTO.class);

        assertEquals("John", userDto.getFirstName());
        assertEquals("Doe", userDto.getLastName());
        assertEquals("john.doe@example.com", userDto.getEmail());

    }

    @Test
    void getUserByIDIT() throws Exception {
        long nonExistingUser = 22222L;
        // Изпълняваме реалната заявка към endpoint-а
        ResponseEntity<ApiResponse> response =
                restTemplate.getForEntity("/api/v1/users/{userId}/user", ApiResponse.class, nonExistingUser);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        ApiResponse body = response.getBody();
        assertNotNull(body);
        assertEquals("User not found", body.getMessage());

        assertNull(body.getData());


    }

    @Test
    void testCreateUserIT_Success() {
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setFirstName("John");
        userRequest.setLastName("Doe");
        userRequest.setEmail("john.doe@email.com");
        userRequest.setPassword("password");

        ResponseEntity<ApiResponse> response =
                restTemplate.postForEntity("/api/v1/users/add", userRequest, ApiResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        ApiResponse body = response.getBody();
        assertNotNull(body);

        // Проверка на съобщението в отговора
        assertEquals("Created User Successfully!", body.getMessage());

        UserDetailsDTO userDto = objectMapper.convertValue(body.getData(), UserDetailsDTO.class);


        assertEquals("John", userDto.getFirstName());
        assertEquals("Doe", userDto.getLastName());
        assertEquals("john.doe@email.com", userDto.getEmail());

    }





    @Test
    void testCreateUser_Failure_UserAlreadyExists() throws Exception {
        // Подготвяме съществуващ потребител
        CreateUserRequest existingUser = new CreateUserRequest();
        existingUser.setFirstName("John");
        existingUser.setLastName("Doe");
        existingUser.setEmail("john.doe@email.com");
        existingUser.setPassword("password");

        // Първи опит - успешен
        restTemplate.postForEntity("/api/v1/users/add", existingUser, ApiResponse.class);

        ResponseEntity<ApiResponse> response =
                restTemplate.postForEntity("/api/v1/users/add", existingUser, ApiResponse.class);


        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        ApiResponse body = response.getBody();
        assertNotNull(body);

        assertEquals("Oops! " + existingUser.getEmail() + " already exists.", body.getMessage());

    }


    @Test
    void testUpdateUser_Success() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setFirstName("John");
        existingUser.setLastName("Doe");
        existingUser.setEmail("john.doe@email.com");
        existingUser.setPassword("password");

        userRepository.save(existingUser);

        // 2️⃣ Подготвяме заявка за ъпдейт
        UserUpdateRequest request = new UserUpdateRequest();
        request.setFirstName("UpdatedJohn");
        request.setLastName("UpdatedDoe");


        // 3️⃣ Добавяме JWT токен (фалшив за теста)
        String jwtToken = "Bearer faketoken123";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwtToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));


        ResponseEntity<ApiResponse> response = restTemplate.exchange(

                "/api/v1/users/update",
                HttpMethod.PUT,
                new HttpEntity<>(request, headers),
                ApiResponse.class
        );


        assertEquals(HttpStatus.OK, response.getStatusCode());


        ApiResponse body = response.getBody();
        assertNotNull(body);

        UserUpdateUsernameDTO userUpdateDTO = objectMapper.convertValue(body.getData(), UserUpdateUsernameDTO.class);

        assertEquals("UpdatedJohn", userUpdateDTO.getFirstName());
        assertEquals("Profile Updated Successfully!", body.getMessage());

    }


}
