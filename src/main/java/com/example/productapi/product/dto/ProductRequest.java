package com.example.productapi.product.dto;

// TODO Day 2 lab (slide "Request body and ResponseEntity"): the JSON body for create (POST) and update (PUT)
// The components: name (String), price (BigDecimal), quantity (int), category (String).
// Then the rules (slide "Validate at the edge"). The Postman suite checks the messages for name and price,
// so use these exactly:
//   name      @NotBlank "Name is required", @Size(max = 100) "Name must be at most 100 characters"
//   price     @NotNull "Price is required", @DecimalMin("0.01") "Price must be at least 0.01",
//             @Digits(integer = 8, fraction = 2) "Price can have at most 2 decimals"
//   quantity  @PositiveOrZero "Quantity cannot be negative"
//   category  @NotBlank "Category is required", @Size(max = 50) "Category must be at most 50 characters",
//             and your own @Capitalized "Category must start with a capital letter"
public record ProductRequest() {
}
