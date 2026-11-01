package com.example.productapi;

// TODO Day 4 lab (slide "Integration tests guard the rules"): the whole app, security included
// @SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:product-test") and @AutoConfigureMockMvc.
// Six tests: anonymousRequestIsRejected (401), userCanReadButCannotCreate (200, then 403),
// adminCanCreateUpdateAndDelete, sortParameterChangesTheOrder, unknownSortPropertyIsRejected (400)
// and categoryListsCheapestFirst.
class ProductApiIntegrationTest {
}
