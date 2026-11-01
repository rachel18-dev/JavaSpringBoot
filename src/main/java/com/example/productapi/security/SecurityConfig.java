package com.example.productapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

// Done for you until Day 4: everything is open, so you can try the API in Postman without signing in.
//
// TODO Day 4 lab (slides "Spring Security architecture: the filter chain", "Security rules: first match wins"
// and "Password encoding with BCrypt"): replace the open rule with
//   - two users in an InMemoryUserDetailsManager, passwords hashed with BCryptPasswordEncoder:
//       user / user123   roles USER
//       admin / admin123 roles USER and ADMIN
//   - HTTP Basic, used only to get a token: POST /api/auth/token (TokenController)
//   - a JwtEncoder and a JwtDecoder (Nimbus, HS256) built from app.jwt.secret (AppProperties);
//     read the roles from the token's "roles" claim with the prefix "ROLE_"
//   - stateless sessions, CSRF off (the API uses no cookies)
//   - the rules, first match wins:
//       /, /index.html, /app.js, /app.css  everyone (the bonus UI)
//       /actuator/health, /actuator/info   everyone
//       POST /api/auth/token               signed in
//       GET /api/products/**               USER or ADMIN
//       /api/products/**                   ADMIN
//       anything else                      signed in
@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }
}
