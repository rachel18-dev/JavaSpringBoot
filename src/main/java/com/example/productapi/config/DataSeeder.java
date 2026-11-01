package com.example.productapi.config;

// TODO Day 2 lab (slide "@SpringBootApplication and the startup sequence"): sample data for dev
// A @Configuration with @Profile("dev") (dev is the default profile, see application.yml)
// and a @Bean CommandLineRunner that saves these three products, only when the table is empty.
// The Postman suite expects them:
//   name                 price   quantity  category
//   Mechanical Keyboard   89.99    25      Electronics
//   Wireless Mouse        29.50    40      Electronics
//   Standing Desk        349.00     5      Furniture
public class DataSeeder {
}
