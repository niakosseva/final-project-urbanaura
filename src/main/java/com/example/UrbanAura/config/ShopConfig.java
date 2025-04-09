package com.example.UrbanAura.config;

import com.example.UrbanAura.user.ShopUserDetailsService;
import com.example.UrbanAura.user.jwt.AuthTokenFilter;
import com.example.UrbanAura.user.jwt.JwtAuthEntryPoint;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity
public class ShopConfig {

    private final ShopUserDetailsService userDetailsService;
    private final JwtAuthEntryPoint authEntryPoint;

    private static final List<String> SECURED_URLS = List.of("/api/v1/carts/**",
            "/api/v1/cartItems/**");

    public ShopConfig(ShopUserDetailsService userDetailsService, JwtAuthEntryPoint authEntryPoint) {
        this.userDetailsService = userDetailsService;
        this.authEntryPoint = authEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.requestMatchers(SECURED_URLS.toArray(String[]::new)).authenticated()
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/vendor/**", "/fonts/**", "/icons/**").permitAll()
                        .requestMatchers("/api/images/**").permitAll() // Allow access to image API
                        .requestMatchers(
                                "/",
                                "/index",
                                "/user/login",
                                "/user/account-register",
                                "/about",
                                "/clothing-categories/**",
                                "/item/**",
                                "/shopping-cart",
                                "/wishlist/**",
                                "/my-order/checkout",
                                "/help",
                                "/user/user-profile",
                                "/contact").permitAll()
                        .requestMatchers("/api/v1/cartItems/**").authenticated()
                        .requestMatchers("/admins/admin/**", "/admins/user-orders").hasRole("ADMIN")
                        .anyRequest().permitAll());
        httpSecurity.authenticationProvider(daoAuthenticationProvider());
        httpSecurity.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public PasswordEncoder encoder() {
        return Pbkdf2PasswordEncoder
                .defaultsForSpringSecurity_v5_8();
    }


    @Bean
    public AuthTokenFilter authTokenFilter() {
        return new AuthTokenFilter(); // Pass required dependencies
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;

    }


}
