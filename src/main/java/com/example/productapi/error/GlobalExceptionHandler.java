package com.example.productapi.error;

// TODO Day 2 lab (slides "Global exception handling: @RestControllerAdvice" and "One error format: RFC 9457
// problem details")
// A @RestControllerAdvice with one @ExceptionHandler per error, each returning a ProblemDetail.
// The Postman suite checks the titles, so use these exactly:
//   ProductNotFoundException          404  title "Product not found"
//   PropertyReferenceException        400  title "Invalid sort"       (?sort=banana)
//   MethodArgumentNotValidException   400  title "Validation failed", plus a property "errors":
//                                          a map of field name to message, e.g. "name" -> "Name is required"
// PropertyReferenceException is in the package org.springframework.data.core.
public class GlobalExceptionHandler {
}
