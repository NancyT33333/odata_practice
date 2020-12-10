package ru.teamidea.odatapractice.products.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;



@Entity
@Table(name = "ORDERS")

public class Order {
    
    @Id
    @Column(name = "ID")
    @GeneratedValue
    @NotBlank
    private Long id;
    
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long param) {
        this.id = param;
    }
    
    
    @OneToMany(mappedBy = "order",  targetEntity = OrderItem.class)
    private List<OrderItem> orderItems;

    public Order() {
        this.orderItems = new ArrayList<OrderItem>();
    }
    
    @Column(name = "SURNAME")
    private String surName;
    
    public String getSurName() {
        return this.surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }
   
    
    @Column(name = "FIRSTNAME")
    private String firstName;
    
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    @Column(name = "ADDRESS")
    private String address;
    
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    @Column(name = "PHONE")
    private String phone;
    
    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    @Column(name = "EMAIL")
    private String email;
    
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
        
//    @Column(name="(select SURNAME, FIRSTNAME from ORDERS) as fullName",insertable = false, updatable = false)
//    public String getFullName() {
//        return fullName;
//    }
//    
// 
//    transient private String fullName;
//    
//    public String getFullName() {
//        return this.firstName + this.surName;
//    }
//
//    public void setFullName(String fullName) {
//        this.fullName = fullName;
//    }   

}
