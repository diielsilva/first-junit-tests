package com.olix.stock.resource.controller;

import com.olix.stock.domain.exception.BusinessException;
import com.olix.stock.domain.exception.ConstraintException;
import com.olix.stock.domain.exception.ModelNotFoundException;
import com.olix.stock.domain.model.Product;
import com.olix.stock.domain.service.ProductService;
import com.olix.stock.resource.representation.product.output.ShowProduct;
import com.olix.stock.resource.representation.standard.ResponseMessage;
import com.olix.stock.util.ProductFactory;
import com.olix.stock.util.mapper.ProductMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class ProductControllerTest {
    @InjectMocks
    private ProductController controller;
    @Mock
    private ProductService service;
    @Mock
    private ProductMapper mapper;

    @BeforeEach
    void setUp() {
        setUpToFindAll();
        setUpToFindById();

        BDDMockito.doNothing().when(service).save(ArgumentMatchers.any());

        BDDMockito.when(mapper.convertFromSaveProductToProduct(ArgumentMatchers.any())).thenReturn(ProductFactory.createProductToBeSaved());
    }

    private void setUpToFindAll() {
        PageImpl<Product> productPage = new PageImpl<>(List.of(ProductFactory.createProductWithValidId()));
        BDDMockito.when(service.findAll(ArgumentMatchers.any())).thenReturn(productPage);
        PageImpl<ShowProduct> showProductPage = new PageImpl<>(List.of(ProductFactory.createShowProduct()));
        BDDMockito.when(mapper.convertFromProductPageToShowProductPage(ArgumentMatchers.any())).thenReturn(showProductPage);
    }

    private void setUpToFindById() {
        Product product = ProductFactory.createProductWithValidId();
        BDDMockito.when(service.findById(ArgumentMatchers.anyLong())).thenReturn(product);
        ShowProduct showProduct = ProductFactory.createShowProduct();
        BDDMockito.when(mapper.convertFromProductToShowProduct(ArgumentMatchers.any())).thenReturn(showProduct);
    }

    private void setUpToFindByNameContaining() {
        PageImpl<Product> productPage = new PageImpl<>(List.of(ProductFactory.createProductWithValidId()));
        BDDMockito.when(service.findByNameContaining(ArgumentMatchers.any(), ArgumentMatchers.anyString())).thenReturn(productPage);
        PageImpl<ShowProduct> showProductPage = new PageImpl<>(List.of(ProductFactory.createShowProduct()));
        BDDMockito.when(mapper.convertFromProductPageToShowProductPage(ArgumentMatchers.any())).thenReturn(showProductPage);
    }

    @DisplayName("Return page of products when successful")
    @Test
    void returnProductPageWhenSuccessful() {
        Page<ShowProduct> showProductPage = controller.findAll(null).getBody();
        String expectedProductName = ProductFactory.createShowProduct().getName();
        assertThat(showProductPage).isNotNull();
        assertThat(showProductPage.getContent()).isNotNull().isNotEmpty().hasSize(1);
        assertThat(showProductPage.getContent().get(0).getName()).isEqualTo(expectedProductName);
    }

    @DisplayName("Return a product to show when a valid ID is given")
    @Test
    void returnShowProductWhenFindByIdIsSuccessful() {
        ShowProduct showProduct = controller.findById(null).getBody();
        String expectedName = ProductFactory.createShowProduct().getName();
        assertThat(showProduct).isNotNull();
        assertThat(showProduct.getName()).isEqualTo(expectedName);
    }

    @DisplayName("Return a page of products when search is successful")
    @Test
    void returnShowProductPageWhenSearchIsSuccessful() {
        String expectedName = ProductFactory.createShowProduct().getName();
        Page<ShowProduct> showProductPage = controller.findByNameContaining(null, null).getBody();
        assertThat(showProductPage).isNotNull().isNotEmpty().hasSize(1);
        assertThat(showProductPage.getContent().get(0).getName()).isEqualTo(expectedName);
    }

    @DisplayName("Return an empty page of products when search is unsuccessful")
    @Test
    void returnAnEmptyShowProductPageWhenSearchIsUnsuccessful() {
        PageImpl<Product> productPage = new PageImpl<>(Collections.emptyList());
        BDDMockito.when(service.findByNameContaining(ArgumentMatchers.any(), ArgumentMatchers.anyString())).thenReturn(productPage);
        Page<ShowProduct> showProductPage = new PageImpl<>(Collections.emptyList());
        BDDMockito.when(mapper.convertFromProductPageToShowProductPage(ArgumentMatchers.any())).thenReturn(showProductPage);
        Page<ShowProduct> finalShowProductPage = controller.findByNameContaining(null, null).getBody();
        assertThat(finalShowProductPage).isNotNull().isEmpty();
    }

    @DisplayName("Return nothing when product is saved successful")
    @Test
    void returnNothingWhenProductIsSaved() {
        assertThatCode(() -> this.controller.save(null)).doesNotThrowAnyException();
        ResponseEntity<Void> responseEntity = this.controller.save(null);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @DisplayName("Return exception when product is not found")
    @Test
    void returnExceptionWhenIdIsNotFound() {
        BDDMockito.when(service.findById(ArgumentMatchers.anyLong())).thenThrow(new ModelNotFoundException("Product not found"));
        assertThatThrownBy(() -> controller.findById(1L)).isInstanceOf(ModelNotFoundException.class).hasMessage("Product not found");
    }
}