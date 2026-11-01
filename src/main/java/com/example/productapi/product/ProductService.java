package com.example.productapi.product;

// TODO Day 2 lab (slide "Model–View–Controller in the product API"): the business layer
//   - @Service; take the ProductRepository in the constructor (constructor injection)
//   - findAll(Pageable), findById(Long), create(ProductRequest), update(Long, ProductRequest), delete(Long)
//   - return ProductResponse (or a Page of them), never the entity
//   - strip() the name and category before saving
//   - a missing id throws ProductNotFoundException: update and delete check first
//   - update: set the entity's fields, then repository.save(product)
// Day 3 adds the queries, the default order, transactions and logging.
public class ProductService {
}
