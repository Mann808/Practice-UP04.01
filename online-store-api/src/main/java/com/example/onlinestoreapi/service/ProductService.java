package com.example.onlinestoreapi.service;

import com.example.onlinestoreapi.entity.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product createProduct(Product product);
    List<Product> getAllProducts();
    Optional<Product> getProductById(Long id);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
}