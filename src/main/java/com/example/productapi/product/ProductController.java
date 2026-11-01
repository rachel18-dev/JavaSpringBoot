package com.example.productapi.product;

// TODO Day 2 lab (slides "The REST endpoints: full CRUD", "GET, POST, PUT and DELETE",
// "Path variables and query parameters" and "Request body and ResponseEntity")
// A @RestController at /api/products that calls ProductService:
//   GET    /api/products        200, a page: ?page=0&size=20&sort=price,desc
//   GET    /api/products/{id}   200 (404 through GlobalExceptionHandler)
//   POST   /api/products        201 Created, with a Location header pointing to the new product
//   PUT    /api/products/{id}   200, the updated product
//   DELETE /api/products/{id}   204 No Content
// The list takes a @PageableDefault(size = 20) Pageable; POST and PUT take a @Valid @RequestBody ProductRequest.
// Day 3 adds ?category=, /search and /categories.
public class ProductController {
}
