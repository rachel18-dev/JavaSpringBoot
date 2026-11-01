package com.example.productapi.product.dto;

// TODO Day 2 lab (slide "DTOs and model mapping"): the JSON the API returns for a product
// The components: id (Long), name (String), price (BigDecimal), quantity (int), category (String),
// createdAt (Instant). Add a static from(Product product) method that copies the entity's fields,
// so the service never returns the entity itself.
public record ProductResponse() {
}
