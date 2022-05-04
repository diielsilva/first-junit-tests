package com.olix.stock.resource.representation.product.input;

import javax.validation.constraints.NotBlank;

public class SaveProduct {
    @NotBlank(message = "Name can't be empty, null or blank")
    private String name;

    public SaveProduct() {

    }

    public SaveProduct(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
