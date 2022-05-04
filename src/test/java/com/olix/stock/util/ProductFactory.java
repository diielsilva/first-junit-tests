package com.olix.stock.util;

import com.olix.stock.domain.model.Product;
import com.olix.stock.resource.representation.product.output.ShowProduct;

public class ProductFactory {

    public static Product createProductWithInvalidName() {
        return new Product(null, "", 1L);
    }

    public static Product createProductToBeSaved() {
        return new Product(null, "iPhone 3GS", 0L);
    }

    public static Product createProductWithValidId() {
        return new Product(1L, "Samsung Book", 0L);
    }


    public static ShowProduct createShowProduct() {
        return new ShowProduct(1L, "Samsung Book", 0L);
    }
}
