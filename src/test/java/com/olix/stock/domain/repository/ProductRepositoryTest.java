package com.olix.stock.domain.repository;

import com.olix.stock.domain.model.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryTest {
    @Autowired
    private ProductRepository repository;

    public static final String WRONG_PRODUCT_NAME = "Sushi";
    public static final String CORRECT_COMPLETE_PRODUCT_NAME = "Samsung Book";
    public static final String CORRECT_NAME_FOR_SEARCH = "Samsung";

    @DisplayName(value = "Return nothing when a wrong product name is given")
    @Test
    void returnNothingWhenSearchedProductIsNotFound() {
        Optional<Product> databaseProduct = this.repository.findByName(WRONG_PRODUCT_NAME);
        assertThat(databaseProduct).isNotNull().isEmpty();
    }

    @DisplayName(value = "Return a single product when a correct product name is given")
    @Test
    void returnProductWhenSearchedProductIsFound() {
        Optional<Product> databaseProduct = this.repository.findByName(CORRECT_COMPLETE_PRODUCT_NAME);
        assertThat(databaseProduct).isNotNull().isNotEmpty();
        assertThat(databaseProduct.get().getName()).isEqualTo(CORRECT_COMPLETE_PRODUCT_NAME);
    }

    @DisplayName(value = "Return an empty page of products when a wrong product name is given")
    @Test
    void returnAnEmptyPageWhenSearchedProductIsNotFound() {
        Page<Product> searchedProducts = this.repository.findByNameContaining(getPageable(), WRONG_PRODUCT_NAME);
        assertThat(searchedProducts).isNotNull().isEmpty();
    }

    @DisplayName(value = "Return a not empty page of products when a correct product name is given")
    @Test
    void returnANotEmptyPageWhenSearchedProductIsNotFound() {
        Page<Product> searchedProducts = this.repository.findByNameContaining(getPageable(), CORRECT_NAME_FOR_SEARCH);
        assertThat(searchedProducts).isNotNull().isNotEmpty().hasSize(1);
        assertThat(searchedProducts.getContent().get(0).getName()).contains(CORRECT_NAME_FOR_SEARCH);
    }

    private Pageable getPageable() {
        return PageRequest.of(0, 20);
    }
}