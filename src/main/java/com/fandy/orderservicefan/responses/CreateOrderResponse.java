package com.fandy.orderservicefan.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateOrderResponse {

    @JsonProperty("order_id")
    private String orderId;

    private String status;
    private String message;

    public CreateOrderResponse() {}

    public CreateOrderResponse(String orderId, String status, String message) {
        this.orderId = orderId;
        this.status = status;
        this.message = message;
    }

    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
