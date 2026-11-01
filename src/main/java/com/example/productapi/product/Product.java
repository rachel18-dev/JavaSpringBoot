package com.example.productapi.product;

// TODO Day 2 lab (slides "Model–View–Controller in the product API" and "Entity and repository")
// The model: a JPA entity for the "products" table, @Entity and @Table(name = "products"), with
//   id         Long        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//   name       String      not null, at most 100 characters
//   price      BigDecimal  not null, precision 10, scale 2
//   quantity   int         not null
//   category   String      not null, at most 50 characters
//   createdAt  Instant     not null, never updated; set it in a @PrePersist method
// Add a protected no-argument constructor (JPA needs it), a constructor taking name, price, quantity
// and category, getters for every field, and setters for name, price, quantity and category.
public class Product {
}
