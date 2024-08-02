package com.example.UrbanAura.config;

import com.example.UrbanAura.services.AppUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig  {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(
                        // Setup which URL-s are available to who
                        authorizeRequests ->
                                authorizeRequests
                                        // all static resources to "common locations" (css, images, js) are available to anyone
                                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                        // some more resources for all users
                                        .requestMatchers("/", "/users/login","/users/login-error", "/users/account-register", "/about").permitAll()
                                        // all other URL-s should be authenticated.
//                                        .requestMatchers("/admin/**").hasRole("ADMIN")
                                        .anyRequest()
                                        .authenticated()
                )
                .formLogin(formLogin -> {
                    formLogin.loginPage("/users/login");
                    formLogin.usernameParameter("email");
                    formLogin.passwordParameter("password");
                    formLogin.defaultSuccessUrl("/", true);
                    formLogin.failureForwardUrl("/users/login-error");


                })
                .logout(logout -> logout
                        // what is the logout URL?
                        .logoutUrl("/users/logout")
                        // Where to go after successful logout?
                        .logoutSuccessUrl("/")
                        // invalidate the session after logout.
                        .invalidateHttpSession(true)
                )
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder, AppUserDetailsService appUserDetailsService)
            throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(appUserDetailsService).passwordEncoder(passwordEncoder);
        return builder.build();
    }

    @Bean
    public PasswordEncoder encoder() {
        return Pbkdf2PasswordEncoder
                .defaultsForSpringSecurity_v5_8();
    }


}
//Another way of setting user details service
//    @Bean
//    public UrbanAuraDetailsService userDetailsService(UserRepository userRepository) {
//        return new UrbanAuraDetailsService(userRepository);
//    }



