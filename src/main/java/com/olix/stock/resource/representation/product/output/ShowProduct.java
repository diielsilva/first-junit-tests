package com.olix.stock.resource.representation.product.output;

public class ShowProduct {
    private Long id;
    private String name;
    private Long amount;

    public ShowProduct() {

    }

    public ShowProduct(Long id, String name, Long amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getAmount() {
        return amount;
    }
}
