package ru.teamidea.odatapractice.products.models;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "PRODUCT")
public class Product {
    
    @Id
    @Column(name = "PRODUCT_ID", length = 10)
    @NotBlank
    private BigDecimal id;

    @Column(name = "NAME")
    private String name;
    
    @Column(name = "PICTURE")
    private String picture;
    
    @Column(name = "CURRENCY", length = 3)
    private String currency;

    
    @Column(name="PRICE", precision = 23, scale = 3)
    private BigDecimal price;

    public Product(BigDecimal id) {
        this.id = id;
    }
    

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal param) {
        this.price = param;
    }
    
    public void setCurrency(String param) {
        this.currency = param;
    }

    public String getCurrency() {
        return this.currency;
    }
    
    public String getPicture() {
        return this.picture;
    }

    public void setPicture(String param) {
        this.picture = param;
    }

}