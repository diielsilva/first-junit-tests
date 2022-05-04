package com.olix.stock.domain.service.impl;

import com.olix.stock.domain.repository.ProductRepository;
import com.olix.stock.domain.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ProductServiceImplTest {
    @InjectMocks
    private ProductService service;
    @Mock
    private ProductRepository repository;

    @BeforeEach
    void setUp() {
        BDDMockito.doNothing().when(repository).save(ArgumentMatchers.any());
    }

    @DisplayName("ensure the product has been saved successfully")
    @Test
    void ensureTheProductHasBeenSavedSuccessfully() {

    }

    @Test
    void isNameInUse() {
    }

    @Test
    void findById() {
    }
}