package com.mirkamolcode.repository;

import com.mirkamolcode.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    List<Product> findAll();
    Optional<Product> findById(UUID id);
    boolean existsById(UUID id);
    void deleteById(UUID id);
    void save(Product product);
}
