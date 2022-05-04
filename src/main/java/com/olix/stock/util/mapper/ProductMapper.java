package com.olix.stock.util.mapper;

import com.olix.stock.domain.model.Product;
import com.olix.stock.resource.representation.product.input.SaveProduct;
import com.olix.stock.resource.representation.product.input.UpdateProduct;
import com.olix.stock.resource.representation.product.output.ShowProduct;
import org.springframework.data.domain.Page;

public interface ProductMapper {
    Product convertFromSaveProductToProduct(SaveProduct saveProduct);

    ShowProduct convertFromProductToShowProduct(Product product);

    Page<ShowProduct> convertFromProductPageToShowProductPage(Page<Product> productPage);

    Product convertFromUpdateProductToProduct(UpdateProduct updateProduct);
}
