package com.fandy.orderservicefan.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("orders")
public class Order {

    @Id
    private String orderId;

    private String customerId;
    private String itemId;
    private Integer quantity;
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
