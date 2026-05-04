package com.fandy.orderservicefan.responses;

public class ItemResponse {

    private String itemId;
    private String name;
    private Integer price;
    private Integer availableQuantity;

    public ItemResponse(String itemId, String name, Integer price, Integer availableQuantity) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.availableQuantity = availableQuantity;
    }

    public String getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }
}