//package com.example.UrbanAura.auth;
//
//
//import com.example.UrbanAura.services.AppUserDetailsService;
//import com.example.UrbanAura.user.UrbanAuraUserDetails;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//
//@Component
//public class CustomAuthenticationFailureHandler implements AuthenticationProvider {
//
//    private final AppUserDetailsService appUserDetailsService;
//    private final PasswordEncoder passwordEncoder;
//
//    public CustomAuthenticationFailureHandler(AppUserDetailsService appUserDetailsService, PasswordEncoder passwordEncoder) {
//        this.appUserDetailsService = appUserDetailsService;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String email = authentication.getName();
//        String password = authentication.getCredentials().toString();
//        try {
//            UrbanAuraUserDetails userDetails = (UrbanAuraUserDetails) appUserDetailsService.loadUserByUsername(email);
//            if (passwordEncoder.matches(password, userDetails.getPassword())) {
//                return new UsernamePasswordAuthenticationToken(email, password, userDetails.getAuthorities());
//            } else {
//                throw new BadCredentialsException("Invalid email or password.");
//            }
//        } catch (UsernameNotFoundException e) {
//            throw new BadCredentialsException("Invalid email or password.");
//        }
//
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
//
//    }
//}
