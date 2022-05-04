package com.olix.stock.domain.repository;

import com.olix.stock.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);

    Page<Product> findByNameContaining(Pageable pageable, String name);
}
