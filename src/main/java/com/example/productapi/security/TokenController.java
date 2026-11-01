package com.example.productapi.security;

// TODO Day 4 lab (slide "JWT: what's inside the token"): POST /api/auth/token
// A @RestController. The caller signs in with HTTP Basic; take the Authentication as a parameter and
// return this JSON (a Map is enough):
//   {"access_token": "<the JWT>", "token_type": "Bearer", "expires_in": 3600}
// Build the JWT with JwtEncoder and a JwtClaimsSet: issuer and lifetime from AppProperties, subject =
// the username, and a "roles" claim such as ["USER", "ADMIN"] (the authorities without "ROLE_").
// Sign it with JwsHeader.with(MacAlgorithm.HS256).
public class TokenController {
}
