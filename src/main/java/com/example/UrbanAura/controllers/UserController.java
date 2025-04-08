package com.example.UrbanAura.controllers;

import com.example.UrbanAura.exceptions.AlreadyExistsException;
import com.example.UrbanAura.exceptions.ResourceNotFoundException;
import com.example.UrbanAura.models.dtos.UserDetailsDTO;
import com.example.UrbanAura.models.dtos.UserUpdateUsernameDTO;
import com.example.UrbanAura.models.entities.User;
import com.example.UrbanAura.requests.CreateUserRequest;
import com.example.UrbanAura.requests.UserUpdateRequest;
import com.example.UrbanAura.response.ApiResponse;
import com.example.UrbanAura.services.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.HttpStatus.*;


@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("me/firstName")
    public ResponseEntity<ApiResponse> getCurrentUserFirstName() {
        try {
            User user = userService.getAuthenticatedUser();
            UserDetailsDTO userDto = userService.convertUserToDto(user);

            if (user == null) {
                return ResponseEntity.ok(new ApiResponse("Current user not logged in.", null)); // Или ""
            }

            return ResponseEntity.ok(new ApiResponse("Success", userDto.getFirstName()));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            UserDetailsDTO userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("Success", userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }

    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request) {
        try {
            User user = userService.createUser(request);
            UserDetailsDTO userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("Created User Successfully!", userDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request) {
        try {
            User user = userService.getAuthenticatedUser();
            User updatedUser = userService.updateUser(request, user.getId());
            UserUpdateUsernameDTO userDto = userService.convertUserUpdateToDto(updatedUser);
            return ResponseEntity.ok(new ApiResponse("Profile Updated Successfully!", userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId, @RequestBody Map<String, String> request) {
        try {
            String password = request.get("password");
            userService.deleteUser(userId, password);
            return ResponseEntity.ok(new ApiResponse("Delete User Successfully!", userId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(UNAUTHORIZED)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }
}









