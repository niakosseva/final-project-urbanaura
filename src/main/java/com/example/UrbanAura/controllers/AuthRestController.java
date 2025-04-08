package com.example.UrbanAura.controllers;

import com.example.UrbanAura.requests.LoginRequest;
import com.example.UrbanAura.response.ApiResponse;
import com.example.UrbanAura.response.JwtResponse;
import com.example.UrbanAura.user.UrbanAuraUserDetails;
import com.example.UrbanAura.user.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Map;

@RestController
@RequestMapping("${api.prefix}/auth")
public class AuthRestController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthRestController(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/user/token")
    public ResponseEntity<?> getUserToken(@CookieValue(name = "jwt", required = false) String jwt) {
        if (jwt == null || !jwtUtils.validateToken(jwt)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("authenticated", false));
        }

        return ResponseEntity.ok(Map.of("jwt", jwt));
    }

    @GetMapping("/user/status")
    public ResponseEntity<?> checkUserStatus(@CookieValue(name = "jwt", required = false) String jwt) {
        if (jwt == null || !jwtUtils.validateToken(jwt)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("authenticated", false));
        }
//        String username = jwtUtils.getUsernameFromToken(jwt);
        String firstName = jwtUtils.getFirstNameFromToken(jwt);



        return ResponseEntity.ok(Map.of("authenticated", true, "firstName", firstName));
    }

    @PostMapping("/logout/user")
    public ResponseEntity<ApiResponse> logout(HttpServletRequest request, HttpServletResponse response) {
        ResponseCookie jwtCookie = ResponseCookie.from("jwt", null)
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax")
                .path("/")
                .maxAge(0)
                .build();

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", null)
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax")
                .path("/")
                .maxAge(0)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

        return ResponseEntity.ok(new ApiResponse("Logout successful.", null));
    }

    @PostMapping("/login/user")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateTokenForUser(authentication);
            UrbanAuraUserDetails userDetails = (UrbanAuraUserDetails) authentication.getPrincipal();
            JwtResponse jwtResponse = new JwtResponse(userDetails.getId(), jwt);

            ResponseCookie cookie = ResponseCookie.from("jwt", jwt)
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("Lax")
                    .maxAge(Duration.ofHours(1).getSeconds())
                    .path("/")
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            return ResponseEntity.ok(new ApiResponse("Login successful!", jwtResponse));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("Invalid email or password.", e.getMessage()));
        }
    }
}

