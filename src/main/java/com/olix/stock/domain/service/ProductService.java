package com.olix.stock.domain.service;

import com.olix.stock.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    void save(Product product);

    boolean isNameInUse(String name);

    Product findById(Long id);

    Product findByName(String name);

    Page<Product> findByNameContaining(Pageable pageable, String name);

    void update(Long id, Product product);

    Page<Product> findAll(Pageable pageable);
}
