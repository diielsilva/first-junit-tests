package com.olix.stock.domain.service.impl;

import com.olix.stock.domain.exception.ConstraintException;
import com.olix.stock.domain.exception.ModelNotFoundException;
import com.olix.stock.domain.model.Product;
import com.olix.stock.domain.repository.ProductRepository;
import com.olix.stock.domain.service.ProductService;
import com.olix.stock.util.messenger.ProductMessenger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    private void setAmountToSave(Product product) {
        product.setAmount(0L);
    }

    private void setAmountToUpdate(Product databaseProduct, Product productToUpdate) {
        databaseProduct.setName(productToUpdate.getName());
    }

    @Override
    public void save(Product product) {
        if (isNameInUse(product.getName())) {
            throw new ConstraintException(ProductMessenger.NAME_IN_USE_MESSAGE);
        }
        setAmountToSave(product);
        this.repository.save(product);
    }

    @Override
    public boolean isNameInUse(String name) {
        return this.repository.findByName(name).isPresent();
    }

    @Override
    public Product findById(Long id) {
        Optional<Product> databaseProduct = this.repository.findById(id);
        if (databaseProduct.isEmpty()) {
            throw new ModelNotFoundException(ProductMessenger.PRODUCT_NOT_FOUND_MESSAGE);
        }
        return databaseProduct.get();
    }

    @Override
    public Page<Product> findByNameContaining(Pageable pageable, String name) {
        return this.repository.findByNameContaining(pageable, name);
    }

    @Override
    public Product findByName(String name) {
        Optional<Product> databaseProduct = this.repository.findByName(name);
        if (databaseProduct.isEmpty()) {
            throw new ModelNotFoundException(ProductMessenger.PRODUCT_NOT_FOUND_MESSAGE);
        }
        return databaseProduct.get();
    }

    @Override
    public void update(Long id, Product product) {
        Product databaseProduct = this.findById(id);
        setAmountToUpdate(databaseProduct, product);
        this.repository.save(databaseProduct);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return this.repository.findAll(pageable);
    }
}
