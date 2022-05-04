package com.olix.stock.resource.controller;

import com.olix.stock.domain.model.Product;
import com.olix.stock.domain.service.ProductService;
import com.olix.stock.resource.representation.product.input.SaveProduct;
import com.olix.stock.resource.representation.product.input.UpdateProduct;
import com.olix.stock.resource.representation.product.output.ShowProduct;
import com.olix.stock.util.mapper.ProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/products")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid SaveProduct saveProduct) {
        Product product = this.productMapper.convertFromSaveProductToProduct(saveProduct);
        this.productService.save(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ShowProduct> findById(@PathVariable Long id) {
        Product product = this.productService.findById(id);
        ShowProduct showProduct = this.productMapper.convertFromProductToShowProduct(product);
        return new ResponseEntity<>(showProduct, HttpStatus.OK);
    }

    @GetMapping(path = "/search")
    public ResponseEntity<Page<ShowProduct>> findByNameContaining(@RequestParam String name, Pageable pageable) {
        Page<Product> productPage = this.productService.findByNameContaining(pageable, name);
        Page<ShowProduct> showProductPage = this.productMapper.convertFromProductPageToShowProductPage(productPage);
        return new ResponseEntity<>(showProductPage, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid UpdateProduct updateProduct) {
        Product product = this.productMapper.convertFromUpdateProductToProduct(updateProduct);
        this.productService.update(id, product);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<ShowProduct>> findAll(Pageable pageable) {
        Page<Product> productPage = this.productService.findAll(pageable);
        Page<ShowProduct> showProductPage = this.productMapper.convertFromProductPageToShowProductPage(productPage);
        return new ResponseEntity<>(showProductPage, HttpStatus.OK);
    }

}
