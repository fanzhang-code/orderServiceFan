package com.fandy.orderservicefan.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("orders")
public class Order {

    @Id
    @Column("orderId")
    private String orderId;
    @Column("customerId")
    private String customerId;
    @Column("itemId")
    private String itemId;
    @Column("quantity")
    private Integer quantity;
    @Column("status")
    private String status;

    public Order() {}

    public Order(String orderId, String customerId, String itemId,
                       Integer quantity, String status) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.status = status;
    }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getItemId() { return itemId; }
    public void setItemId(String itemId) { this.itemId = itemId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

}
