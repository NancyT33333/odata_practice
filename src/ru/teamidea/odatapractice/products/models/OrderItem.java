package ru.teamidea.odatapractice.products.models;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;


@Entity
@Table(name = "ORDERITEMS")
public class OrderItem {
	
    public OrderItem(BigDecimal id) {
        this.id = id;
    }
    
    public OrderItem() {
       
    }
  
    @Column(precision = 13, scale = 3)
    private BigDecimal quantity;
    
    @Id
    @NotBlank
    @Column(name = "ID")
    private BigDecimal id;
    
    @Column(name = "PRODUCTID")
    private BigDecimal productId;
    
    @ManyToOne(optional=false)
    @JoinColumn(name = "ORDERID", referencedColumnName = "ID", insertable = false, updatable = false)
    private Order order;
    
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order param) {
        this.order = param;
    }
    
    @Column(name = "ORDERID")
    private BigDecimal orderId;
    
    @OneToOne
    private Product product;
    
    public BigDecimal getQuantity() {
        return this.quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
    
    public BigDecimal getProductId() {
        return this.productId;
    }

    public void setProductId(BigDecimal param) {
        this.productId = param;
    }
    
    
    public BigDecimal getOrderId() {
        return this.orderId;
    }

    public void setOrderId(BigDecimal param) {
        this.orderId = param;
    }

}
