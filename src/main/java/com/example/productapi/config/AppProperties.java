package com.example.productapi.config;

// TODO Day 4 lab (slides "JWT: what's inside the token" and "Prod refuses to start without its secret")
// The JWT settings, read from app.jwt: replace this class with a record annotated
// @Validated and @ConfigurationProperties(prefix = "app.jwt"), with
//   secret  String    @NotBlank @Size(min = 32, message = "must be at least 32 characters; set JWT_SECRET")
//   issuer  String    @NotBlank
//   ttl     Duration  @NotNull
// ProductApiApplication's @ConfigurationPropertiesScan finds it. An unset ${JWT_SECRET} would otherwise be
// used as the literal text "${JWT_SECRET}"; with the check, prod refuses to start.
public class AppProperties {
}
