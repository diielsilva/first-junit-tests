package com.olix.stock.util.mapper.impl;

import com.olix.stock.domain.model.Product;
import com.olix.stock.resource.representation.product.input.SaveProduct;
import com.olix.stock.resource.representation.product.input.UpdateProduct;
import com.olix.stock.resource.representation.product.output.ShowProduct;
import com.olix.stock.util.mapper.ProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class ProductMapperImpl implements ProductMapper {
    @Override
    public Product convertFromSaveProductToProduct(SaveProduct saveProduct) {
        return new Product(saveProduct.getName());
    }

    @Override
    public Page<ShowProduct> convertFromProductPageToShowProductPage(Page<Product> productPage) {
        return productPage.map(this::convertFromProductToShowProduct);
    }

    @Override
    public ShowProduct convertFromProductToShowProduct(Product product) {
        return new ShowProduct(product.getId(), product.getName(), product.getAmount());
    }

    @Override
    public Product convertFromUpdateProductToProduct(UpdateProduct updateProduct) {
        return new Product(updateProduct.getName());
    }
}
