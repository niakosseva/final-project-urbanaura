//package com.example.UrbanAura.config;
//
//import com.example.UrbanAura.user.ShopUserDetailsService;
//import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//
//@Configuration
//public class SecurityConfig {
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .authorizeHttpRequests(
//                        authorizeRequests ->
//                                authorizeRequests
//                                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//                                        .requestMatchers("/vendor/**", "/fonts/**", "/icons/**").permitAll()
//                                        .requestMatchers("/",
//                                                "/index",
//                                                "/user/login",
//                                                "/users/account-registration",
//                                                "/about",
//                                                "/blog",
//                                                "/contact").permitAll()
//                                        .requestMatchers("/api/v1/items/**").permitAll()
//                                        .requestMatchers("/api/v1/images/**").permitAll()
//                                        .requestMatchers("/api/v1/orders/**").permitAll() // Explicitly allow orders
//                                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                                        .requestMatchers("/user/**").hasRole("USER")
//                                        .anyRequest().permitAll()
//                )
//                .formLogin(formLogin -> {
//                    formLogin.loginPage("/user/login");
//                    formLogin.usernameParameter("email");
//                    formLogin.passwordParameter("password");
//                    formLogin.defaultSuccessUrl("/index", true);
//                    formLogin.failureUrl("/user/login?error=true");
//                })
//                .logout(logout -> logout
//                        // what is the logout URL?
//                        .logoutUrl("/user/logout")
//                        // Where to go after successful logout?
//                        .logoutSuccessUrl("/index")
//                        // invalidate the session after logout.
//                        .invalidateHttpSession(true)
//                        .clearAuthentication(true)
//                        .deleteCookies("JSESSIONID")
//                );
//
//        return httpSecurity.build();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http, ShopUserDetailsService shopUserDetailsService, PasswordEncoder
//            passwordEncoder)
//            throws Exception {
//        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
//        builder.userDetailsService(shopUserDetailsService).passwordEncoder(passwordEncoder);
//        return builder.build();
//    }
//
//
//
//
//}
////Another way of setting user details service
////    @Bean
////    public UrbanAuraDetailsService userDetailsService(UserRepository userRepository) {
////        return new UrbanAuraDetailsService(userRepository);
////    }
//
//
//
