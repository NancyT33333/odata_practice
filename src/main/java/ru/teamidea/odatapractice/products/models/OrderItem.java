package ru.teamidea.odatapractice.products.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;


@Entity
@Table(name = "ORDERITEM")
public class OrderItem {
	
    public OrderItem(Long id) {
        this.id = id;
    }
    
    public OrderItem() {
       
    }
    
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long param) {
        this.id = param;
    }
  
    @Column(name = "QUANTITY")
    private Long quantity;
    
    @Id
    @GeneratedValue
    @NotBlank
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "PRODUCTID")
    private Long productId;
    
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
    private Long orderId;
    
    @OneToOne
    private Product product;
    
    public Long getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
    
    public Long getProductId() {
        return this.productId;
    }

    public void setProductId(Long param) {
        this.productId = param;
    }
    
    
    public Long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Long param) {
        this.orderId = param;
    }

}
