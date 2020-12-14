package ru.teamidea.odatapractice.products.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "PRODUCT")
public class Product {
    
    @Id
    @Column(name = "PRODUCT_ID", length = 10)
    @NotBlank
    @GeneratedValue
    private Long id;

    @Column(name = "NAME")
    private String name;
    
    @Column(name = "PICTURE")
    private Byte[] picture;
    
    @Column(name = "CURRENCY", length = 3)
    private String currency;

    
    @Column(name="PRICE", precision = 23, scale = 3)
    private Float price;

    public Product(Long id) {
        this.id = id;
    }
    
    public Product() {
        
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Float getPrice() {
        return this.price;
    }

    public void setPrice(Float param) {
        this.price = param;
    }
    
    public void setCurrency(String param) {
        this.currency = param;
    }

    public String getCurrency() {
        return this.currency;
    }
    
    public Byte[] getPicture() {
        return this.picture;
    }

    public void setPicture(Byte[] param) {
        this.picture = param;
    }

}