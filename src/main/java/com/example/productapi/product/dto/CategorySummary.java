package com.example.productapi.product.dto;

// TODO Day 3 lab (slide "Custom queries with @Query"): one row of the totals per category
// A record with category (String), products (Long) and stockValue (BigDecimal). The @Query in
// ProductRepository builds one per category with "select new ...CategorySummary(...)".
public record CategorySummary() {
}
