package com.fandy.orderservicefan.entity;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class Item {

    private String itemId;
    private String name;
    private Integer price;
    private Integer availableQuantity;

    public Item() {}

    public Item(String itemId, String name, Integer price, Integer availableQuantity) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.availableQuantity = availableQuantity;
    }

    @DynamoDbPartitionKey
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }
}
