package ru.teamidea.odatapractice.products.models;

import org.hibernate.validator.constraints.NotBlank;

public class Product {

    @NotBlank
    private String id;

    public Product() {
    }

    public Product(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String title) {
        this.id = id;
    }
}